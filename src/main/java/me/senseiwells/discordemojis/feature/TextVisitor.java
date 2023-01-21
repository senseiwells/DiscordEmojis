package me.senseiwells.discordemojis.feature;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.senseiwells.discordemojis.mixins.TextVisitFactoryInvoker.visitRegularCharacter;

public class TextVisitor {
	private static final Pattern CUSTOM_EMOJI = Pattern.compile("<:[\\w_]+:(\\d+)>");

	private TextVisitor() { }

	public static boolean visitForwards(String text, Style style, CharacterVisitor visitor) {
		int i = text.length();
		boolean canDrawEmojis = visitor instanceof EmojiVisitor;

		for(int j = 0; j < i; ++j) {
			if (canDrawEmojis) {
				j = parseEmoji(text, j, (EmojiVisitor) visitor);
				if (j >= i) {
					break;
				}
			}

			char c = text.charAt(j);
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

	private static int parseEmoji(String string, int index, EmojiVisitor visitor) {
		Matcher matcher = CUSTOM_EMOJI.matcher(string);
		if (matcher.find(index) && matcher.start() == index && visitor.visit(matcher.group(1))) {
			return matcher.end();
		}
		return index;
	}
}
