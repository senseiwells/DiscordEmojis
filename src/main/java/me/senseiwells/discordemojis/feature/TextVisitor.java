package me.senseiwells.discordemojis.feature;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.senseiwells.discordemojis.mixins.TextVisitFactoryInvoker.visitRegularCharacter;

public class TextVisitor {
	private static final Pattern CUSTOM_EMOJI = Pattern.compile("<:[\\w_]+:(\\d+)>");

	private TextVisitor() { }

	public static boolean visitForwards(String text, Style style, CharacterVisitor visitor) {
		int i = text.length();
		boolean canDrawEmojis = visitor instanceof EmojiVisitor && SharedConstants.SHOULD_RENDER_EMOJIS;

		for (int j = 0; j < i; ++j) {
			char c = text.charAt(j);
			if (canDrawEmojis) {
				int k = parseEmoji(text, j, c, (EmojiVisitor) visitor);
				if (k != j) {
					j = k;
					continue;
				}
			}

			if (Character.isHighSurrogate(c)) {
				if (j + 1 >= i) {
					if (!visitor.accept(j, style, 65533)) {
						return false;
					}
					break;
				}

				char d = text.charAt(j + 1);
				if (Character.isLowSurrogate(d)) {
					if (!visitor.accept(j, style, Character.toCodePoint(c, d))) {
						return false;
					}

					++j;
				} else if (!visitor.accept(j, style, 65533)) {
					return false;
				}
			} else if (!visitRegularCharacter(style, visitor, j, c)) {
				return false;
			}
		}

		return true;
	}

	public static boolean visitFormatted(String text, int startIndex, Style startingStyle, Style resetStyle, CharacterVisitor visitor) {
		int i = text.length();
		Style style = startingStyle;
		boolean canDrawEmojis = visitor instanceof EmojiVisitor && SharedConstants.SHOULD_RENDER_EMOJIS;

		for (int j = startIndex; j < i; ++j) {
			char c = text.charAt(j);
			if (canDrawEmojis) {
				int k = parseEmoji(text, j, c, (EmojiVisitor) visitor);
				if (k != j) {
					j = k;
					continue;
				}
			}

			char d;
			if (c == '§') {
				if (j + 1 >= i) {
					break;
				}
				d = text.charAt(j + 1);
				Formatting formatting = Formatting.byCode(d);
				if (formatting != null) {
					style = formatting == Formatting.RESET ? resetStyle : style.withExclusiveFormatting(formatting);
				}
				++j;
				continue;
			}

			if (Character.isHighSurrogate(c)) {
				if (j + 1 >= i) {
					if (visitor.accept(j, style, 65533)) {
						break;
					}
					return false;
				}
				d = text.charAt(j + 1);
				if (Character.isLowSurrogate(d)) {
					if (!visitor.accept(j, style, Character.toCodePoint(c, d))) {
						return false;
					}
					++j;
					continue;
				}
				if (visitor.accept(j, style, 65533)) {
					continue;
				}
				return false;
			}

			if (visitRegularCharacter(style, visitor, j, c)) {
				continue;
			}
			return false;
		}
		return true;
	}

	// This is kinda slow...
	private static int parseEmoji(String string, int index, char current, EmojiVisitor visitor) {
		if (current == '<') {
			Matcher matcher = CUSTOM_EMOJI.matcher(string);
			if (matcher.find(index) && matcher.start() == index) {
				String emojiId = matcher.group(1);
				if (visitor.visit(index, emojiId)) {
					return matcher.end() - 1;
				}
			}
		}
		return index;
	}
}
