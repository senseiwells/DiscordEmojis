package me.senseiwells.discordemojis.mixins;

import me.senseiwells.discordemojis.feature.TextVisitor;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TextVisitFactory.class)
public class TextVisitFactoryMixin {
	/**
	 * @author Sensei
	 * @reason Apply visiting discord emojis.
	 */
	@Overwrite
	public static boolean visitForwards(String text, Style style, CharacterVisitor visitor) {
		return TextVisitor.visitForwards(text, style, visitor);
	}
}
