package me.senseiwells.discordemojis.mixins;

import me.senseiwells.discordemojis.feature.EmojiGlyph;
import me.senseiwells.discordemojis.feature.EmojiGlyphs;
import me.senseiwells.discordemojis.feature.EmojiVisitor;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net/minecraft/client/font/TextHandler$LineBreakingVisitor")
public abstract class LineBreakingVisitorMixin implements EmojiVisitor {
	@Shadow @Final private float maxWidth;

	@Shadow private Style lastSpaceStyle;
	@Shadow private float totalWidth;
	@Shadow private int count;
	@Shadow private int lastSpaceBreak;
	@Shadow private int startOffset;
	@Shadow private boolean nonEmpty;

	@Shadow
	protected abstract boolean breakLine(int finishIndex, Style finishStyle);

	@Override
	public boolean visit(int index, String emojiId) {
		EmojiGlyph glyph = EmojiGlyphs.getGlyph(emojiId);
		this.totalWidth += glyph.getShift();
		if (this.nonEmpty && this.totalWidth > this.maxWidth) {
			if (this.lastSpaceBreak != -1) {
				return this.breakLine(this.lastSpaceBreak, this.lastSpaceStyle);
			}
			return this.breakLine(index + this.startOffset, this.lastSpaceStyle);
		}
		this.nonEmpty = true;
		this.count++;
		return true;
	}
}
