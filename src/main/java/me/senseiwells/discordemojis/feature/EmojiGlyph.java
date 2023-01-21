package me.senseiwells.discordemojis.feature;

import net.minecraft.client.font.RenderableGlyph;
import net.minecraft.client.texture.NativeImage;

public class EmojiGlyph implements RenderableGlyph {
	private NativeImage emoji;

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
		this.emoji.upload(0, x, y, 0, 0, this.getWidth(), this.getWidth(), true, false);
	}

	@Override
	public boolean hasColor() {
		return true;
	}

	@Override
	public float getOversample() {
		return 10.0F;
	}

	public float getShift() {
		return this.getWidth() / this.getOversample();
	}

	public void setEmoji(NativeImage emoji) {
		this.emoji = emoji;
	}

	//#if MC < 11900
	//$$@Override
	//$$public float getAdvance() {
	//$$	return 0;
	//$$}
	//#endif
}
