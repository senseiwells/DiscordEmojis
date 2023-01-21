package me.senseiwells.discordemojis.feature;

import me.senseiwells.discordemojis.DiscordEmojis;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.GlyphAtlasTexture;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EmojiGlyphs {
	private static final Executor IO_EXECUTOR = Executors.newSingleThreadExecutor();
	private static final NativeImage DEFAULT_EMOJI = generateDefaultEmoji();

	private static final List<GlyphAtlasTexture> ATLASES = new ArrayList<>();
	private static final Map<String, EmojiGlyph> GLYPHS = new HashMap<>();

	public static EmojiGlyph getGlyph(String emojiId) {
		EmojiGlyph glyph = GLYPHS.get(emojiId);
		if (glyph != null) {
			return glyph;
		}
		EmojiGlyph newGlyph = new EmojiGlyph(DEFAULT_EMOJI);
		GLYPHS.put(emojiId, newGlyph);
		IO_EXECUTOR.execute(() -> {
			try {
				URL emojiUrl = new URL("https://cdn.discordapp.com/emojis/" + emojiId + ".png?size=80&quality=lossless");
				NativeImage emoji = NativeImage.read(emojiUrl.openStream());
				MinecraftClient.getInstance().execute(() -> newGlyph.setEmoji(emoji));
			} catch (IOException e) {
				DiscordEmojis.LOGGER.error("Failed to get emoji", e);
			}
		});
		return newGlyph;
	}

	public static GlyphRenderer getGlyphRenderer(EmojiGlyph c) {
		for (GlyphAtlasTexture atlas : ATLASES) {
			GlyphRenderer glyphRenderer = atlas.getGlyphRenderer(c);
			if (glyphRenderer != null) {
				return glyphRenderer;
			}
		}

		Identifier id = new Identifier("discordemojis", "emoji/" + ATLASES.size());
		GlyphAtlasTexture atlas = new GlyphAtlasTexture(id, c.hasColor());
		ATLASES.add(atlas);
		MinecraftClient.getInstance().getTextureManager().registerTexture(atlas.getId(), atlas);
		return atlas.getGlyphRenderer(c);
	}

	private static NativeImage generateDefaultEmoji() {
		NativeImage image = new NativeImage(8, 8, false);
		image.untrack();
		return image;
	}
}
