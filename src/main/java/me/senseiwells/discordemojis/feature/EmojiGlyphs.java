package me.senseiwells.discordemojis.feature;

import me.senseiwells.discordemojis.DiscordEmojis;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.GlyphAtlasTexture;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class EmojiGlyphs {
	private static final List<GlyphAtlasTexture> ATLASES = new ArrayList<>();
	private static final Map<String, EmojiGlyph> GLYPHS = new HashMap<>();

	public static EmojiGlyph getGlyph(String emojiId) {
		EmojiGlyph glyph = GLYPHS.get(emojiId);
		if (glyph != null) {
			return glyph;
		}
		try {
			URL emojiUrl = new URL("https://cdn.discordapp.com/emojis/" + emojiId + ".png?size=20&quality=lossless");
			glyph = new EmojiGlyph(NativeImage.read(emojiUrl.openStream()));
			GLYPHS.put(emojiId, glyph);
			return glyph;
		} catch (IOException e) {
			DiscordEmojis.LOGGER.error("Failed to get emoji", e);
			return null;
		}
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
}
