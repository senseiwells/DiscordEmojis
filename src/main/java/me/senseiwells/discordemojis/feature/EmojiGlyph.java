package me.senseiwells.discordemojis.feature;

import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;

public class EmojiGlyph implements RenderableGlyph {
	private final NativeImage emoji;

	public EmojiGlyph(NativeImage emoji) {
		this.emoji = emoji;
	}

	@Override
	public int getWidth() {
		return this.emoji.getWidth();
	}

	@Override
	public int getHeight() {
		return this.emoji.getHeight();
	}

	@Override
	public void upload(int x, int y) {
		this.emoji.upload(0, x, y, false);
	}

	@Override
	public boolean hasColor() {
		return true;
	}

	@Override
	public float getOversample() {
		return 1.0F;
	}

	//#if MC < 11900
	//$$@Override
	//$$public float getAdvance() {
	//$$	return 0;
	//$$}
	//#endif
}
