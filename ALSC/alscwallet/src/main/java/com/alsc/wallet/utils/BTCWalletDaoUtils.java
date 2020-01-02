package com.alsc.wallet.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.helper.BTCWalletHelper;
import com.alsc.wallet.R;
import com.alsc.wallet.bean.GenerateTransactionResult;
import com.alsc.wallet.btcCoin.btc.BTCUtils;
import com.alsc.wallet.btcCoin.btc.BitcoinException;
import com.alsc.wallet.btcCoin.btc.KeyPair;
import com.alsc.wallet.btcCoin.btc.Transaction;
import com.alsc.wallet.btcCoin.btc.UnspentOutputInfo;
import com.mirko.androidutil.utils.android.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by WuQuan
 * 比特币钱包相关
 */

public class BTCWalletDaoUtils {

    private static KeyPair verifiedKeyPairForTx;
    private static ArrayList<UnspentOutputInfo> verifiedUnspentOutputsForTx;
    private static boolean jsonInput;
    private static String jsonParseError;
    private static long verifiedAmountToSendForTx = -1;
    private static final long SEND_MAX = -1;
    private static final long AMOUNT_ERR = -2;

    /**
     * 插入新创建钱包
     *
     * @param btcWallet 新创建钱包
     */
    public static void insertNewWallet(BtcWallet btcWallet) {
        updateCurrent(-1);
        btcWallet.setIsCurrent(true);
        BTCWalletHelper.getInstance().insertObject(btcWallet);
    }

    /**
     * 更新选中钱包
     *
     * @param id 钱包ID
     */
    public static BtcWallet updateCurrent(long id) {
        List<BtcWallet> btcWallets = BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
        BtcWallet currentWallet = null;
        for (BtcWallet btcWallet : btcWallets) {
            if (id != -1 && btcWallet.getId() == id) {
                btcWallet.setIsCurrent(true);
                currentWallet = btcWallet;
            } else {
                btcWallet.setIsCurrent(false);
            }
            BTCWalletHelper.getInstance().updateObject(btcWallet);
        }
        return currentWallet;
    }

    /**
     * 获取当前钱包
     *
     * @return 钱包对象
     */
    public static BtcWallet getCurrent() {
        List<BtcWallet> btcWallets = BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
        for (BtcWallet btcWallet : btcWallets) {
            if (btcWallet.getIsCurrent()) {
                btcWallet.setIsCurrent(true);
                return btcWallet;
            }
        }
        return null;
    }

    /**
     * 查询所有钱包
     */
    public static List<BtcWallet> loadAll() {
        return BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
    }

    /**
     * 检查钱包名称是否存在
     *
     * @param name
     * @return
     */
    public static boolean walletNameChecking(String name) {
        List<BtcWallet> btcWallets = loadAll();
        for (BtcWallet btcWallet : btcWallets
        ) {
            if (TextUtils.equals(btcWallet.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置isBackup为已备份
     *
     * @param walletId 钱包Id
     */
    public static void setIsBackup(long walletId) {
        BtcWallet btcWallet = BTCWalletHelper.getInstance().QueryById(walletId, BtcWallet.class);
        btcWallet.setIsBackup(true);
        BTCWalletHelper.getInstance().updateObject(btcWallet);
    }

    /**
     * 以助记词检查钱包是否存在
     *
     * @param mnemonic
     * @return true if repeat
     */
    public static boolean checkRepeatByMenmonic(String mnemonic) {
        List<BtcWallet> btcWallets = loadAll();
        for (BtcWallet btcWallet : btcWallets
        ) {
            if (TextUtils.isEmpty(btcWallet.getMnemonic())) {
                LogUtils.d("wallet mnemonic empty");
                continue;
            }
            if (TextUtils.equals(btcWallet.getMnemonic().trim(), mnemonic.trim())) {
                LogUtils.d("aleady");
                return true;
            }
        }
        return false;
    }


    public static boolean isValid(String mnemonic) {
        return mnemonic.split(" ").length >= 12;
    }

    public static boolean checkRepeatByKeystore(String keystore) {
        return false;
    }

    /**
     * 修改钱包名称
     *
     * @param walletId
     * @param name
     */
    public static void updateWalletName(long walletId, String name) {
        BtcWallet wallet = BTCWalletHelper.getInstance().QueryById(walletId, BtcWallet.class);
        wallet.setName(name);
        BTCWalletHelper.getInstance().updateObject(wallet);
    }

    public static void setCurrentAfterDelete() {
        List<BtcWallet> btcWallets = BTCWalletHelper.getInstance().QueryAll(BtcWallet.class);
        if (btcWallets != null && btcWallets.size() > 0) {
            BtcWallet btcWallet = btcWallets.get(0);
            btcWallet.setIsCurrent(true);
            BTCWalletHelper.getInstance().updateObject(btcWallet);
        }
    }

    /**通过签名生成hex
     * @param unspentOutputsInfoStr auxo
     * @param btcPrivateKey         私钥
     * @param sendAddress           发送地址
     * @param receiveAddress        发送地址
     * @param balance               发送金额
     * @return
     */
    public static String signatureBtcTranstion(String unspentOutputsInfoStr, String btcPrivateKey, String sendAddress,String receiveAddress,long balance) {
        if (!TextUtils.isEmpty(btcPrivateKey)) {
            try {
                //解码私钥
                BTCUtils.PrivateKeyInfo privateKeyInfo = BTCUtils.decodePrivateKey(btcPrivateKey, false);
                if (privateKeyInfo != null) {
                    verifiedKeyPairForTx = new KeyPair(privateKeyInfo, com.alsc.wallet.btcCoin.btc.Address.PUBLIC_KEY_TO_ADDRESS_LEGACY);
                }
                return analysisUnSpendInfo(unspentOutputsInfoStr, verifiedKeyPairForTx,sendAddress,receiveAddress,balance);

            } catch (Exception e) {
                LogUtils.d("签名异常:" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * 解析Unspent数据
     *
     * @param unspentOutputsInfoStr
     * @param keyPair
     */
    public static String analysisUnSpendInfo(String unspentOutputsInfoStr, KeyPair keyPair, String sendAddress,String btcReceiveAddress,long balance) {
        try {
            if (keyPair.address == null) {
                throw new RuntimeException("Address is null in decodeUnspentOutputsInfoTask");
            }
            byte[] outputScriptWeAreAbleToSpend = Transaction.Script.buildOutput(keyPair.address.addressString).bytes;
            ArrayList<UnspentOutputInfo> unspentOutputs = new ArrayList<>();
            //1. decode tx or json
            String txs = unspentOutputsInfoStr.trim();
            byte[] startBytes = txs.length() < 8 ? null : BTCUtils.fromHex(txs.substring(0, 8));
            if (startBytes != null && startBytes.length == 4) {
                String[] txList = txs.split("\\s+");
                for (String rawTxStr : txList) {
                    rawTxStr = rawTxStr.trim();
                    if (rawTxStr.length() > 0) {
                        byte[] rawTx = BTCUtils.fromHex(rawTxStr);
                        if (rawTx != null && rawTx.length > 0) {
                            Transaction baseTx = Transaction.decodeTransaction(rawTx);
                            byte[] rawTxReconstructed = baseTx.getBytes();
                            if (!Arrays.equals(rawTxReconstructed, rawTx)) {
                                throw new IllegalArgumentException("Unable to decode given transaction");
                            }
                            jsonInput = false;
                            byte[] txHash = baseTx.hash();
                            for (int outputIndex = 0; outputIndex < baseTx.outputs.length; outputIndex++) {
                                Transaction.Output output = baseTx.outputs[outputIndex];
                                if (Arrays.equals(outputScriptWeAreAbleToSpend, output.scriptPubKey.bytes)) {
                                    unspentOutputs.add(new UnspentOutputInfo(keyPair, txHash, output.scriptPubKey, output.value, outputIndex));
                                }
                            }
                        }
                    }
                }
            } else {
                String jsonStr = unspentOutputsInfoStr.replace((char) 160, ' ').trim();//remove nbsp
                if (!jsonStr.startsWith("{")) {
                    jsonStr = "{" + jsonStr;
                }
                if (!jsonStr.endsWith("}")) {
                    jsonStr += "}";
                }
                JSONObject jsonObject = new JSONObject(jsonStr);
                jsonInput = true;
                JSONArray unspentOutputsArray = jsonObject.getJSONArray("unspent_outputs");
                if (unspentOutputsArray == null) {
                    jsonParseError = Utils.getApp().getString(R.string.json_err_no_unspent_outputs);
                }
                for (int i = 0; i < unspentOutputsArray.length(); i++) {
                    JSONObject unspentOutput = unspentOutputsArray.getJSONObject(i);
                    byte[] txHash = BTCUtils.reverse(BTCUtils.fromHex(unspentOutput.getString("tx_hash")));
                    Transaction.Script script = new Transaction.Script(BTCUtils.fromHex(unspentOutput.getString("script")));
                    if (Arrays.equals(outputScriptWeAreAbleToSpend, script.bytes)) {
                        long value = unspentOutput.getLong("value");
                        int outputIndex = (int) unspentOutput.getLong("tx_output_n");
                        unspentOutputs.add(new UnspentOutputInfo(keyPair, txHash, script, value, outputIndex));
                    }
                }
            }
            jsonParseError = null;
            verifiedUnspentOutputsForTx = unspentOutputs;
            if (unspentOutputs == null) {
                if (jsonInput && !TextUtils.isEmpty(jsonParseError)) {
                    //rawTxToSpendErr.setText(getString(R.string.error_unable_to_decode_json_transaction, jsonParseError));
                } else {
                    //rawTxToSpendErr.setText(R.string.error_unable_to_decode_transaction);
                }
            } else if (unspentOutputs.isEmpty()) {
                //rawTxToSpendErr.setText(getString(R.string.error_no_spendable_outputs_found, keyPair.address));
            } else {
                //rawTxToSpendErr.setText("");
                long availableAmount = 0;
                for (UnspentOutputInfo unspentOutputInfo : unspentOutputs) {
                    availableAmount += unspentOutputInfo.value;
                }
                //生成交易
                GenerateTransactionResult result = tryToGenerateSpendingTransaction(btcReceiveAddress,balance);
                if (result != null) {
                    if (result.btcTx != null) {
                        String amountStr = null;
                        Transaction.Script out = null;
                        try {
                            out = Transaction.Script.buildOutput(btcReceiveAddress);
                        } catch (BitcoinException ignore) {
                        }
                        if (result.btcTx.outputs[0].scriptPubKey.equals(out)) {
                            amountStr = BTCUtils.formatValue(result.btcTx.outputs[0].value);
                        }
                        if (amountStr == null) {
                            LogUtils.d("签名信息:未知错误");
                        } else {
                            //签名信息
                            String singatureInfo = BTCUtils.toHex(result.btcTx.getBytes());
                            LogUtils.d("签名信息:" + singatureInfo);
                            return singatureInfo;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.d("解析Unspent数据:" + e.getMessage());
        }
        return null;
    }

    /**
     * 尝试生成支出交易
     */
    private  static GenerateTransactionResult tryToGenerateSpendingTransaction(String btcReceiveAddress,long balance) {
        final ArrayList<UnspentOutputInfo> unspentOutputs = verifiedUnspentOutputsForTx;//unspent,Json 数据集合
        final String outputAddress = btcReceiveAddress;//验证过的收币地址
        final long requestedAmountToSend = balance; //经过计算后的要发送的金额
        final KeyPair keyPair = verifiedKeyPairForTx;
        Transaction btcSpendTx;
        Transaction bchSpendTx = null;
        if (unspentOutputs != null && !unspentOutputs.isEmpty() && !TextUtils.isEmpty(outputAddress) &&
                keyPair != null && keyPair.address != null && requestedAmountToSend >= SEND_MAX && requestedAmountToSend != 0
                && !TextUtils.isEmpty(keyPair.address.addressString)) {
            try {
                //1.遍历unspentOutputs集合数据，获取可以消费的数量
                long availableAmount = 0;
                for (UnspentOutputInfo unspentOutputInfo : unspentOutputs) {
                    availableAmount += unspentOutputInfo.value;
                }
                //2.判断可消费数量是否和发送数量一致，一致，就代表发送全部，不相等，就改成需要发送的数量
                long amount;
                if (availableAmount == requestedAmountToSend || requestedAmountToSend == SEND_MAX) {
                    amount = -1;
                } else {
                    amount = requestedAmountToSend;
                }

                //3.每一个byte多少中本聪
                float satoshisPerVirtualByte = 7;

                btcSpendTx = BTCUtils.createTransaction(
                        unspentOutputs,
                        outputAddress,
                        keyPair.address.addressString,
                        amount,
                        satoshisPerVirtualByte,//每一个byte多少中本聪
                        BTCUtils.TRANSACTION_TYPE_SEGWIT//隔离验证
                );
                try {
                    //解码收币地址
                    com.alsc.wallet.btcCoin.btc.Address outputAddressDecoded = com.alsc.wallet.btcCoin.btc.Address.decode(outputAddress);
                    if (outputAddressDecoded != null && outputAddressDecoded.keyhashType != com.alsc.wallet.btcCoin.btc.Address.TYPE_P2SH) { //this check prevents sending BCH to SegWit
                        bchSpendTx = BTCUtils.createTransaction(unspentOutputs,
                                outputAddress, keyPair.address.addressString, amount, satoshisPerVirtualByte, BTCUtils.TRANSACTION_TYPE_BITCOIN_CASH);
                    }
                } catch (Exception ignored) {
                    LogUtils.d("解码收币地址异常:" + ignored.getMessage());
                }

                //4. double check that generated transaction is valid
                Transaction.Script[] relatedScripts = new Transaction.Script[btcSpendTx.inputs.length];
                long[] amounts = new long[btcSpendTx.inputs.length];
                for (int i = 0; i < btcSpendTx.inputs.length; i++) {
                    Transaction.Input input = btcSpendTx.inputs[i];
                    //验证输出，和输入
                    for (UnspentOutputInfo unspentOutput : unspentOutputs) {
                        if (Arrays.equals(unspentOutput.txHash, input.outPoint.hash) && unspentOutput.outputIndex == input.outPoint.index) {
                            relatedScripts[i] = unspentOutput.scriptPubKey;
                            amounts[i] = unspentOutput.value;
                            break;
                        }
                    }
                }
                //验证btc交易
                BTCUtils.verify(relatedScripts, amounts, btcSpendTx, false);
                if (bchSpendTx != null) {
                    //验证比特币现金交易
                    BTCUtils.verify(relatedScripts, amounts, bchSpendTx, true);
                }
            } catch (BitcoinException e) {
                switch (e.errorCode) {
                    case BitcoinException.ERR_INSUFFICIENT_FUNDS:
                        ToastUtils.showToast(Utils.getApp().getString(R.string.error_not_enough_funds));
                        return new GenerateTransactionResult(Utils.getApp().getString(R.string.error_not_enough_funds), GenerateTransactionResult.ERROR_SOURCE_AMOUNT_FIELD);
                    case BitcoinException.ERR_FEE_IS_TOO_BIG:
                        ToastUtils.showToast(Utils.getApp().getString(R.string.fee_is_greater_than_available_balance));
                        return new GenerateTransactionResult(Utils.getApp().getString(R.string.generated_tx_have_too_big_fee), GenerateTransactionResult.ERROR_SOURCE_INPUT_TX_FIELD);
                    case BitcoinException.ERR_AMOUNT_TO_SEND_IS_LESS_THEN_ZERO:
                        ToastUtils.showToast(Utils.getApp().getString(R.string.fee_is_greater_than_available_balance));
                        return new GenerateTransactionResult(Utils.getApp().getString(R.string.fee_is_greater_than_available_balance), GenerateTransactionResult.ERROR_SOURCE_INPUT_TX_FIELD);
                    case BitcoinException.ERR_MEANINGLESS_OPERATION://input, output and change addresses are same.
                        ToastUtils.showToast(Utils.getApp().getString(R.string.output_address_same_as_input));
                        return new GenerateTransactionResult(Utils.getApp().getString(R.string.output_address_same_as_input), GenerateTransactionResult.ERROR_SOURCE_ADDRESS_FIELD);
                    default:
                        ToastUtils.showToast(Utils.getApp().getString(R.string.error_failed_to_create_transaction));
                        return new GenerateTransactionResult(Utils.getApp().getString(R.string.error_failed_to_create_transaction) + ": " + e.getMessage(), GenerateTransactionResult.ERROR_SOURCE_UNKNOWN);
                }
            } catch (Exception e) {
                ToastUtils.showToast(Utils.getApp().getString(R.string.error_failed_to_create_transaction));
                return new GenerateTransactionResult(Utils.getApp().getString(R.string.error_failed_to_create_transaction) + ": " + e, GenerateTransactionResult.ERROR_SOURCE_UNKNOWN);
            }

            long inValue = 0;
            for (Transaction.Input input : btcSpendTx.inputs) {
                for (UnspentOutputInfo unspentOutput : unspentOutputs) {
                    if (Arrays.equals(unspentOutput.txHash, input.outPoint.hash) && unspentOutput.outputIndex == input.outPoint.index) {
                        inValue += unspentOutput.value;
                    }
                }
            }
            long outValue = 0;
            for (Transaction.Output output : btcSpendTx.outputs) {
                outValue += output.value;
            }
            //矿工费
            long fee = inValue - outValue;
            LogUtils.d("矿工费:"+fee);
            return new GenerateTransactionResult(btcSpendTx, bchSpendTx, fee);
        }
        return null;
    }
}
