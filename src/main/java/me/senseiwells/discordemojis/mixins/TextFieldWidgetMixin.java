package me.senseiwells.discordemojis.mixins;

import me.senseiwells.discordemojis.feature.SharedConstants;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
	@Inject(method = "renderButton", at = @At("HEAD"))
	private void preRenderButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		SharedConstants.SHOULD_RENDER_EMOJIS = false;
	}

	@Inject(method = "renderButton", at = @At("RETURN"))
	private void postRenderButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		SharedConstants.SHOULD_RENDER_EMOJIS = true;
	}
}
