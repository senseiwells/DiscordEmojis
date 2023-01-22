package me.senseiwells.discordemojis.mixins;

import me.senseiwells.discordemojis.feature.EmojiGlyph;
import me.senseiwells.discordemojis.feature.EmojiGlyphs;
import me.senseiwells.discordemojis.feature.EmojiVisitor;
import net.minecraft.client.font.GlyphRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net/minecraft/client/font/TextRenderer$Drawer")
public class DrawerMixin implements EmojiVisitor {
	@Shadow @Final private TextRenderer.TextLayerType layerType;
	@Shadow @Final private Matrix4f matrix;
	@Shadow @Final private float red;
	@Shadow @Final private float green;
	@Shadow @Final private float blue;
	@Shadow @Final private float alpha;
	@Shadow @Final private int light;

	@Shadow @Final VertexConsumerProvider vertexConsumers;

	@Shadow float x;
	@Shadow float y;

	@Override
	public boolean visit(int index, String emojiId) {
		EmojiGlyph glyph = EmojiGlyphs.getGlyph(emojiId);
		GlyphRenderer renderer = EmojiGlyphs.getGlyphRenderer(glyph);
		VertexConsumer consumer = this.vertexConsumers.getBuffer(renderer.getLayer(this.layerType));
		renderer.draw(false, this.x, this.y, this.matrix, consumer, this.red, this.green, this.blue, this.alpha, this.light);
		this.x += glyph.getShift();
		return true;
	}
}
