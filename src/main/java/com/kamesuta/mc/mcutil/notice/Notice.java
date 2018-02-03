package com.kamesuta.mc.mcutil.notice;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kamesuta.mc.mcutil.Config;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * タスクトレイ常駐アプリサンプル
 */
public class Notice implements INotice {
	private TrayIcon icon;

	/** コンストラクタ */
	public Notice() {
		try {
			// タスクトレイアイコン
			final Minecraft mc = Minecraft.getMinecraft();
			final Image image = ImageIO.read(mc.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_16x16.png")));
			final TrayIcon ico = new TrayIcon(image);

			// ポップアップメニュー
			final PopupMenu menu = new PopupMenu();
			// メニューの例
			final MenuItem aItem = new MenuItem(Config.getConfig().notice.get() ? "ON→OFF" : "OFF→ON");
			aItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					Config.getConfig().notice.set(!Config.getConfig().notice.get());
					aItem.setLabel(Config.getConfig().notice.get() ? "ON→OFF" : "OFF→ON");
				}
			});
			// メニューにメニューアイテムを追加
			menu.add(aItem);
			ico.setPopupMenu(menu);
			// タスクトレイに格納
			SystemTray.getSystemTray().add(ico);

			this.icon = ico;
		} catch (final IOException e) {
		} catch (final AWTException e) {
		}
	}

	@Override
	public void notice(final String title, final String message) {
		if (this.icon!=null)
			this.icon.displayMessage(title, message, MessageType.INFO);
	}
}