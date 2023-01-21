package me.senseiwells.discordemojis.mixins;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.Style;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextVisitFactory.class)
public interface TextVisitFactoryInvoker {
	@SuppressWarnings("unused")
	@Invoker("visitRegularCharacter")
	static boolean visitRegularCharacter(Style style, CharacterVisitor visitor, int index, char c) {
		throw new AssertionError();
	}
}
