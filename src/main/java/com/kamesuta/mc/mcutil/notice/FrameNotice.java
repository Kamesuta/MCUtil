package com.kamesuta.mc.mcutil.notice;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FrameNotice implements INotice {

	public FrameNotice() {
		final Dimension size = new Dimension(400, 500);

		final JFrame frame = new JFrame();
		frame.setTitle("Chat");
		frame.setSize(size);
		final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(new Point(d.width-size.width, d.height-size.height));
		try {
			final Minecraft mc = Minecraft.getMinecraft();
			if (mc != null) {
				final Image image = ImageIO.read(mc.mcDefaultResourcePack.func_152780_c(new ResourceLocation("icons/icon_32x32.png")));
				frame.setIconImage(image);
			}
		} catch(final IOException e) {}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0,0,0,0));

		final NPanel npanel = new NPanel();
		frame.setContentPane(npanel);

		frame.getRootPane().setBorder(new LineBorder(Color.black, 2));
		frame.pack();

		frame.setVisible(true);
	}

	class NPanel extends JPanel {

	}

	@Override
	public void notice(final String title, final String message) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
