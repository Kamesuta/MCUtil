package com.kamesuta.mc.mcutil.screenshot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kamesuta.mc.mcutil.HttpRepository;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;

public class Action {
	private final Minecraft minecraft = Minecraft.getMinecraft();

	public void screenShot() {
		try {
			final BufferedImage image = ScreenShot.getScreenshot(
					this.minecraft.displayWidth,
					this.minecraft.displayHeight,
					this.minecraft.getFramebuffer()
					);
			final File file = ScreenShot.getSaveFile(this.minecraft.mcDataDir, null);
			ScreenShot.save(file, image);
			this.minecraft.ingameGUI.getChatGUI().printChatMessage(ScreenShot.getSuccessMessage(file));
			this.minecraft.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText("  and Uploading..."), 114514);

			new Thread("McUtilScreenShotUploader") {
				@Override
				public void run() {
					try {
						final String uri = "http://share.files.kamesuta.com/uploader.php";
						final List<HttpRepository.HttpRepositoryResult> results = HttpRepository.upload(uri, file);

						for (final HttpRepository.HttpRepositoryResult result : results) {
							if (StringUtils.equals(result.status, "完了")) {
								if (result.results != null) {
									final ChatComponentText chatcomponenttext = new ChatComponentText(result.results.name);
									chatcomponenttext.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://files.kamesuta.com/" + result.results.name)).setUnderlined(true);

									Action.this.minecraft.ingameGUI.getChatGUI().deleteChatLine(114514);
									Action.this.minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Uploaded with ").appendSibling(chatcomponenttext));
								}
							} else {
								Action.this.minecraft.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText("Uploading Failed."), 114514);
							}
						}


					} catch (final Exception e) {
						Action.this.minecraft.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText("Uploading Failed. " + e.getMessage()), 114514);
					}
				}
			}.start();
		} catch(final Exception e) {
			this.minecraft.ingameGUI.getChatGUI().printChatMessage(ScreenShot.getFailure(e));
		}
	}
}
