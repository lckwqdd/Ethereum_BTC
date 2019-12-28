package com.alsc.wallet.eth;

import android.util.Log;

import com.alsc.wallet.bean.KeyStoreBean;
import com.alsc.wallet.utils.KeyStoreUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.ObjectMapperFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gyx on 2018/4/27.
 */
public class EthWalletUtil {

	/**
	 * 通用的以太坊基于bip44协议的助记词路径 （imtoken jaxx Metamask myetherwallet）
	 * m / purpose' / coin_type' / account' / change / address_index
	 */
	public final static String ETH_TYPE = "m/44'/60'/0'/0/0";
	/**
	 * 导入助记词
	 * @param path
	 * @param list
	 * @param password
	 * @return
	 */
	public static EthHDWallet importMnemonic(String path, List<String> list, String password) {
		if (!path.startsWith("m") && !path.startsWith("M")) {
			//参数非法
			return null;
		}
		String[] pathArray = path.split("/");
		if (pathArray.length <= 1) {
			//内容不对
			return null;
		}
		if (password.length() < 8) {
			//密码过短
			//return null;
		}
		String passphrase = "";
		long creationTimeSeconds = System.currentTimeMillis() / 1000;
		//用到了bitcoinJ的seed
		DeterministicSeed ds = new DeterministicSeed(list, null, passphrase, creationTimeSeconds);

		return createEthWallet(ds, pathArray, password);
	}



	/**
	 * 创建钱包
	 * @param ds
	 * @param pathArray
	 * @param password
	 * @return
	 */
	private static EthHDWallet createEthWallet(DeterministicSeed ds, String[] pathArray, String password) {
		//根私钥
		byte[] seedBytes = ds.getSeedBytes();
		Log.e("恢复","根私钥 " + Arrays.toString(seedBytes));
		//BTC助记词
		List<String> mnemonic = ds.getMnemonicCode();
		Log.e("恢复","助记词 " + Arrays.toString(mnemonic.toArray()));
		// TODO: 2018/5/2 下面注释掉的代码，不影响生成wallet
		/*try {
			//BTC助记词种子
			byte[] mnemonicSeedBytes = MnemonicCode.INSTANCE.toEntropy(mnemonic);
			Log.e("恢复","助记词种子 " + Arrays.toString(mnemonicSeedBytes));
			//借助BTC的种子，创建ETH的ECKeyPair
			ECKeyPair mnemonicKeyPair = ECKeyPair.create(mnemonicSeedBytes);
			WalletFile walletFile = Wallet.createLight(password, mnemonicKeyPair);
			ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
			//存这个keystore 用完后删除
			String jsonStr = objectMapper.writeValueAsString(walletFile);
			Log.e("恢复","mnemonic keystore " + jsonStr);

			//验证
			WalletFile checkWalletFile = objectMapper.readValue(jsonStr, WalletFile.class);
			ECKeyPair ecKeyPair = Wallet.decrypt(password, checkWalletFile);
			byte[] checkMnemonicSeedBytes = Numeric.hexStringToByteArray(ecKeyPair.getPrivateKey().toString(16));

			Log.e("恢复","验证助记词种子 " + Arrays.toString(checkMnemonicSeedBytes));

			List<String> checkMnemonic = MnemonicCode.INSTANCE.toMnemonic(checkMnemonicSeedBytes);

			Log.e("恢复","验证助记词 " + Arrays.toString(checkMnemonic.toArray()));

		} catch (MnemonicException.MnemonicLengthException | MnemonicException.MnemonicWordException | MnemonicException.MnemonicChecksumException | CipherException | IOException e) {
			e.printStackTrace();
		}*/

		if (seedBytes == null)
			return null;
		DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);
		for (int i = 1; i < pathArray.length; i++) {
			ChildNumber childNumber;
			if (pathArray[i].endsWith("'")) {
				int number = Integer.parseInt(pathArray[i].substring(0, pathArray[i].length() - 1));
				childNumber = new ChildNumber(number, true);
			} else {
				int number = Integer.parseInt(pathArray[i]);
				childNumber = new ChildNumber(number, false);
			}
			dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
		}
		Log.e("恢复","path " + dkKey.getPathAsString());

		ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());
		//保存到本机
		KeyStoreUtils.genKeyStore2Files(keyPair);

		Log.e("恢复","eth privateKey " + keyPair.getPrivateKey().toString(16));
		Log.e("恢复","eth publicKey " + keyPair.getPublicKey().toString(16));

		//---------------------------------24个助记词-----------------------------------------
		/*byte[] checkMnemonicSeedBytes = Numeric.hexStringToByteArray(keyPair.getPrivateKey().toString(16));
		Log.e("恢复--验证助记词种子 " , Arrays.toString(checkMnemonicSeedBytes));
		List<String> checkMnemonic = null;
		try {
			checkMnemonic = MnemonicCode.INSTANCE.toMnemonic(checkMnemonicSeedBytes);
			Log.e("恢复--验证助记词 " ,Arrays.toString(checkMnemonic.toArray()));
		} catch (MnemonicException.MnemonicLengthException e) {
			e.printStackTrace();
		}*/
		//----------------------------------------------------------------------
		EthHDWallet ethHDWallet = null;
		try {
			WalletFile walletFile = Wallet.createLight(password, keyPair);
			Log.e("恢复","eth address " + "0x" + walletFile.getAddress());
			ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
			//存
			String jsonStr = objectMapper.writeValueAsString(walletFile);
			Log.e("恢复","eth keystore " + jsonStr);

			ethHDWallet = new EthHDWallet(
					keyPair.getPrivateKey().toString(16),
					keyPair.getPublicKey().toString(16),
					mnemonic,
					dkKey.getPathAsString(),
					"0x" + walletFile.getAddress(),
					jsonStr);
		} catch (CipherException | JsonProcessingException e) {
			e.printStackTrace();
		}

		return ethHDWallet;
	}

	///////////////////////////////////////////////////////////////////////////
	//
	///////////////////////////////////////////////////////////////////////////
	public static class EthHDWallet {
		String privateKey;
		String publicKey;
		List<String> mnemonic;
		String mnemonicPath;
		String Address;
		String keystore;

		public String getPrivateKey() {
			return privateKey;
		}

		public String getPublicKey() {
			return publicKey;
		}

		public String getAddress() {
			return Address;
		}

		public EthHDWallet(String privateKey, String publicKey, List<String> mnemonic, String mnemonicPath, String address, String keystore) {
			this.privateKey = privateKey;
			this.publicKey = publicKey;
			this.mnemonic = mnemonic;
			this.mnemonicPath = mnemonicPath;
			this.Address = address;
			this.keystore = keystore;
		}
	}

	/**
	 * 获取本地存储的keyStore
	 * @return
	 */
	public static List<KeyStoreBean> gekeystoreList() {


		List<KeyStoreBean> keyStoreBeans = new ArrayList<>();

		File[] keyStoreFiles = KeyStoreUtils.getKeyStorePathFile().listFiles();

		for (File file : keyStoreFiles) {

			String name = file.getName();
			String address;
			if (name.endsWith(".json")){//web3j生成的keystore
				address = name.substring(name.lastIndexOf("--") + 2, name.lastIndexOf("."));
			}else {
				//geth生成的keystore
				address = name.substring(name.lastIndexOf("--") + 2, name.length()-1);
			}


			keyStoreBeans.add(new KeyStoreBean(address, file.getAbsolutePath()));
		}

		return keyStoreBeans;
	}
}
