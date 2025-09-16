package com.pp.augmenta.attachment;

import com.pp.augmenta.Augmenta;
import com.pp.augmenta.augment.AugmentInventory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModAttachments {
    private ModAttachments() {}

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Augmenta.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<AugmentInventory>> AUGMENTS =
            ATTACHMENTS.register("player_augments", () -> AttachmentType
                    .serializable(AugmentInventory::new)
                    .copyOnDeath()
                    .build());

    public static void register(IEventBus eventBus) {
        ATTACHMENTS.register(eventBus);
    }
}
