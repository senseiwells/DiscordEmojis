package me.senseiwells.discordemojis.feature;

public interface EmojiVisitor {
	boolean visit(int index, String emojiId);
}
