package me.senseiwells.discordemojis.mixins;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net/minecraft/client/font/TextHandler$WidthLimitingVisitor")
public class WidthLimitingVisitorMixin /* implements EmojiVisitor */ {
	/* Maybe not needed? See TextFieldWidgetMixin...
	@Shadow private float widthLeft;
	@Shadow private int length;

	@Override
	public boolean visit(int index, String emojiId) {
		this.widthLeft -= EmojiGlyphs.getGlyph(emojiId).getShift();
		this.length++;
		return true;
	}
	 */
}
