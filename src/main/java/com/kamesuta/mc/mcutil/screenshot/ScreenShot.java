package com.kamesuta.mc.mcutil.screenshot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

@SideOnly(Side.CLIENT)
public class ScreenShot
{
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	/** A buffer to hold pixel values returned by OpenGL. */
	private static IntBuffer pixelBuffer;
	/** The built-up array that contains all the pixel values returned by OpenGL. */
	private static int[] pixelValues;

	public static File getSaveFile(final File mcdir, final String name) {
		final File file2 = new File(mcdir, "screenshots");
		file2.mkdir();
		File file3;

		if (name == null)
		{
			file3 = getTimestampedPNGFileForDirectory(file2);
		}
		else
		{
			file3 = new File(file2, name);
		}

		return file3;
	}

	public static void save(final File path, final BufferedImage image) throws IOException {
		ImageIO.write(image, "png", path);
	}

	public static IChatComponent getSuccessMessage(final File file) {
		final ChatComponentText chatcomponenttext = new ChatComponentText(file.getName());
		chatcomponenttext.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath()));
		chatcomponenttext.getChatStyle().setUnderlined(Boolean.valueOf(true));
		return new ChatComponentTranslation("screenshot.success", new Object[] {chatcomponenttext});
	}

	public static IChatComponent getFailure(final Exception exception) {
		return new ChatComponentTranslation("screenshot.failure", new Object[] {exception.getMessage()});
	}

	public static BufferedImage getScreenshot(int width, int height, final Framebuffer framebuffer)
	{
		if (OpenGlHelper.isFramebufferEnabled())
		{
			width = framebuffer.framebufferTextureWidth;
			height = framebuffer.framebufferTextureHeight;
		}

		final int k = width * height;

		if (pixelBuffer == null || pixelBuffer.capacity() < k)
		{
			pixelBuffer = BufferUtils.createIntBuffer(k);
			pixelValues = new int[k];
		}

		GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		pixelBuffer.clear();

		if (OpenGlHelper.isFramebufferEnabled())
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebuffer.framebufferTexture);
			GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
		}
		else
		{
			GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
		}

		pixelBuffer.get(pixelValues);
		TextureUtil.func_147953_a(pixelValues, width, height);
		BufferedImage bufferedimage = null;

		if (OpenGlHelper.isFramebufferEnabled())
		{
			bufferedimage = new BufferedImage(framebuffer.framebufferWidth, framebuffer.framebufferHeight, 1);
			final int l = framebuffer.framebufferTextureHeight - framebuffer.framebufferHeight;

			for (int i1 = l; i1 < framebuffer.framebufferTextureHeight; ++i1)
			{
				for (int j1 = 0; j1 < framebuffer.framebufferWidth; ++j1)
				{
					bufferedimage.setRGB(j1, i1 - l, pixelValues[i1 * framebuffer.framebufferTextureWidth + j1]);
				}
			}
		}
		else
		{
			bufferedimage = new BufferedImage(width, height, 1);
			bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
		}

		return bufferedimage;
	}

	/**
	 * Creates a unique PNG file in the given directory named by a timestamp.  Handles cases where the timestamp alone
	 * is not enough to create a uniquely named file, though it still might suffer from an unlikely race condition where
	 * the filename was unique when this method was called, but another process or thread created a file at the same
	 * path immediately after this method returned.
	 */
	public static File getTimestampedPNGFileForDirectory(final File screenshotdir)
	{
		final String s = dateFormat.format(new Date()).toString();
		int i = 1;

		while (true)
		{
			final File file2 = new File(screenshotdir, s + (i == 1 ? "" : "_" + i) + ".png");

			if (!file2.exists())
			{
				return file2;
			}

			++i;
		}
	}
}