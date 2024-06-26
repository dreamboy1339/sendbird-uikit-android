package com.sendbird.uikit.activities.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.sendbird.android.channel.BaseChannel
import com.sendbird.android.message.BaseMessage
import com.sendbird.android.message.Reaction
import com.sendbird.uikit.annotation.MessageViewHolderExperimental
import com.sendbird.uikit.consts.ClickableViewIdentifier
import com.sendbird.uikit.databinding.SbViewMyMessageBinding
import com.sendbird.uikit.interfaces.EmojiReactionHandler
import com.sendbird.uikit.interfaces.OnItemClickListener
import com.sendbird.uikit.interfaces.OnItemLongClickListener
import com.sendbird.uikit.internal.extensions.toComponentListContextThemeWrapper
import com.sendbird.uikit.model.MessageListUIParams

/**
 * This ViewHolder has a basic message template for 'My message.'
 * To use it, inherit from this ViewHolder, inflate the view corresponding to the content, and pass it to the constructor.
 *
 * @see [com.sendbird.uikit.activities.adapter.MessageListAdapter]
 * @see [com.sendbird.uikit.providers.AdapterProviders.messageList]
 * @since 3.12.0
 */
@MessageViewHolderExperimental
open class MyMessageViewHolder(
    parent: ViewGroup,
    open val contentView: View,
    messageListUIParams: MessageListUIParams,
    private val binding: SbViewMyMessageBinding = SbViewMyMessageBinding.inflate(
        LayoutInflater.from(parent.context.toComponentListContextThemeWrapper()),
    )
) : MessageViewHolder(binding.root, messageListUIParams), EmojiReactionHandler {

    init {
        binding.root.attachContentView(contentView)
    }

    @CallSuper
    override fun bind(channel: BaseChannel, message: BaseMessage, params: MessageListUIParams) {
        binding.root.messageUIConfig = messageUIConfig
        binding.root.drawMessage(channel, message, params)
    }

    @CallSuper
    override fun getClickableViewMap(): Map<String, View> {
        return mapOf(
            ClickableViewIdentifier.Chat.name to binding.root.binding.contentPanel,
            ClickableViewIdentifier.QuoteReply.name to binding.root.binding.quoteReplyPanel,
            ClickableViewIdentifier.ThreadInfo.name to binding.root.binding.threadInfo
        )
    }

    /**
     * Sets message reaction data.
     *
     * @param reactionList List of reactions which the message has.
     * @param emojiReactionClickListener The callback to be invoked when the emoji reaction is clicked and held.
     * @param emojiReactionLongClickListener The callback to be invoked when the emoji reaction is long clicked and held.
     * @param moreButtonClickListener The callback to be invoked when the emoji reaction more button is clicked and held.
     * @since 3.12.0
     */
    final override fun setEmojiReaction(
        reactionList: List<Reaction>,
        emojiReactionClickListener: OnItemClickListener<String>?,
        emojiReactionLongClickListener: OnItemLongClickListener<String>?,
        moreButtonClickListener: View.OnClickListener?
    ) {
        binding.root.binding.rvEmojiReactionList.apply {
            setReactionList(reactionList)
            setEmojiReactionClickListener(emojiReactionClickListener)
            setEmojiReactionLongClickListener(emojiReactionLongClickListener)
            setMoreButtonClickListener(moreButtonClickListener)
        }
    }
}
