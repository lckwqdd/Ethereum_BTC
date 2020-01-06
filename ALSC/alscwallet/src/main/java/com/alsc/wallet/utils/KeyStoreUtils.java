package com.alsc.wallet.utils;

import android.util.Log;

import com.alsc.net.db.bean.ETHWallet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirko.androidutil.utils.android.Utils;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.utils.Numeric;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by pc on 2018/1/25.
 */
public class KeyStoreUtils {
    private static final String Tag = "KeyStoreUtils";
    public static final String DEFAULTKEY = "DEFAULT";
    public static final String KEYSTORE_PATH = Utils.getApp().getFilesDir().getPath() + "/keystore";

    /**
     * 在内置存储生成keystore方便选择
     *
     * @param ecKeyPair
     * @return
     */
    public static String genKeyStore2Files(ECKeyPair ecKeyPair) {
        try {
            File file = getKeyStorePathFile();
            String s = WalletUtils.generateWalletFile(DEFAULTKEY, ecKeyPair, file, false);
            Log.e("gen", s);
            return s;
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据地址，获取资格证书
     *
     * @param tagetAddress
     * @return
     * @throws FileNotFoundException
     */
    public static Credentials getCredentials(String tagetAddress) throws FileNotFoundException {
        File keystorePath = new File(KEYSTORE_PATH);
        File[] files = keystorePath.listFiles();
        for (File file : files) {
            String name = file.getName();
            String address = name.substring(name.lastIndexOf("--") + 2, name.lastIndexOf("."));
            if (tagetAddress.equals(address)) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WalletFile walletFile = mapper.readValue(file, WalletFile.class);
                    //Wallet.decrypt(DEFAULTKEY, walletFile)解码出ECKeyPair
                    return Credentials.create(Wallet.decrypt(DEFAULTKEY, walletFile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        throw new FileNotFoundException("not found keystore(找不到keystore文件)");
    }

    /**
     * 签名交易信息
     *
     * @param from
     * @param to
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param value
     * @return
     * @throws FileNotFoundException
     */
    public static String signedTransactionData(ETHWallet ethWallet,String from, String to, String nonce, String gasPrice, String gasLimit, String value) throws Exception {
        //发送正常交易
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                new BigInteger(nonce),
                new BigInteger(gasPrice),
                new BigInteger(gasLimit),
                to,
                new BigInteger(value)
        );
        //获取资格证书
//      Credentials credentials = KeyStoreUtils.getCredentials(from);
        Credentials credentials = WalletUtils.loadCredentials(ethWallet.getPassword(),  ethWallet.getKeystorePath());
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        return Numeric.toHexString(signedMessage);
    }

    /**以太坊合约交易
     * @param ethWallet
     * @param contractAddress
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param value
     * @param data
     * @return
     * @throws IOException
     * @throws CipherException
     */
    public static String signedTransactionContractData(ETHWallet ethWallet, String contractAddress, String nonce, String gasPrice, String gasLimit, String value, String data) throws IOException, CipherException {
        //发送正常交易
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                new BigInteger(nonce),
                new BigInteger(gasPrice),
                new BigInteger(gasLimit),
                contractAddress,
                new BigInteger(value),
                data
        );
        //获取资格证书
        Credentials credentials = WalletUtils.loadCredentials(ethWallet.getPassword(),  ethWallet.getKeystorePath());
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        return Numeric.toHexString(signedMessage);
    }


    public static File getKeyStorePathFile() {
        File file = new File(KEYSTORE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.e("files", file.getAbsolutePath());
        return file;
    }

    /**
     * 解密keystore 得到ECKeyPair
     * @param keystore
     * @param password
     * @return
     */
    public static ECKeyPair decryptWallet(File keystore, String password) {
        String privateKey = null;
        ECKeyPair ecKeyPair = null;
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            ecKeyPair = Wallet.decrypt(password, walletFile);
            privateKey = ecKeyPair.getPrivateKey().toString(16);
//            System.out.println(privateKey);

        } catch (CipherException e) {
            if ("Invalid password provided".equals(e.getMessage())) {
//                System.out.println("密码错误");
                Log.e(Tag, "密码错误");
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ecKeyPair;
    }

    public static String getAddress(File keystore) {

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            return walletFile.getAddress();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
