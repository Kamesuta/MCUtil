package com.kamesuta.mc.mcutil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kamesuta.mc.mcutil.notice.FrameNotice;

public class Debug {

	public Debug() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static void main(final String[] args) {
		final Logger logger = LogManager.getLogger("McUtilDebug");
		//		final File f = new File("C:/Users/b7n/git/MCUtil/screenshots/2016-05-21_13.35.06.png");
		//		final String uri = "http://share.files.kamesuta.com/uploader.php";
		//		logger.info(HttpRepository.upload(uri, f));

		final FrameNotice n = new FrameNotice();
		n.notice("aa", "aa");
	}

}
