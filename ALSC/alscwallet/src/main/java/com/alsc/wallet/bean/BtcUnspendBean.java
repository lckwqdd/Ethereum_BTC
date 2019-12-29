package com.alsc.wallet.bean;

import java.util.List;

/**
 * Created by gyx on 2018/4/3.
 */
public class BtcUnspendBean {
	private List<UnspentOutputsBean> unspent_outputs;

	public List<UnspentOutputsBean> getUnspent_outputs() {
		return unspent_outputs;
	}

	public void setUnspent_outputs(List<UnspentOutputsBean> unspent_outputs) {
		this.unspent_outputs = unspent_outputs;
	}

	public static class UnspentOutputsBean {
		/**
		 * tx_hash : 6178c72f2b65079c944fa77bdad8b50de7cce057a6adf2d2e39d50c9505896ad
		 * tx_hash_big_endian : ad965850c9509de3d2f2ada657e0cce70db5d8da7ba74f949c07652b2fc77861
		 * tx_index : 195731841
		 * tx_output_n : 0
		 * script : 76a914f65af6aaeb109376da8cbbcd4ed6c4bc9a0ff1bf88ac
		 * value : 20000000
		 * value_hex : 01312d00
		 * confirmations : 3893
		 */
		private String tx_hash;
		private String tx_hash_big_endian;
		private int tx_index;
		private int tx_output_n;
		private String script;
		private int value;
		private String value_hex;
		private int confirmations;

		public String getTx_hash() {
			return tx_hash;
		}

		public void setTx_hash(String tx_hash) {
			this.tx_hash = tx_hash;
		}

		public String getTx_hash_big_endian() {
			return tx_hash_big_endian;
		}

		public void setTx_hash_big_endian(String tx_hash_big_endian) {
			this.tx_hash_big_endian = tx_hash_big_endian;
		}

		public int getTx_index() {
			return tx_index;
		}

		public void setTx_index(int tx_index) {
			this.tx_index = tx_index;
		}

		public int getTx_output_n() {
			return tx_output_n;
		}

		public void setTx_output_n(int tx_output_n) {
			this.tx_output_n = tx_output_n;
		}

		public String getScript() {
			return script;
		}

		public void setScript(String script) {
			this.script = script;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getValue_hex() {
			return value_hex;
		}

		public void setValue_hex(String value_hex) {
			this.value_hex = value_hex;
		}

		public int getConfirmations() {
			return confirmations;
		}

		public void setConfirmations(int confirmations) {
			this.confirmations = confirmations;
		}
	}
}

