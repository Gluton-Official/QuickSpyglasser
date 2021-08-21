package com.gluton.quickspyglasser.command;

import com.gluton.quickspyglasser.QuickSpyglasser;
import com.gluton.quickspyglasser.network.QuickSpyglasserNetwork;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

public class QuickSpyglasserCommand {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            var node = dispatcher.register(CommandManager.literal("quickspyglasser")
                .then(CommandManager.literal("config")
                    .then(CommandManager.literal("quickSpyglassItemId")
                        .executes(QuickSpyglasserCommand::sendCurrentQSItem)
                        .then(CommandManager.argument("item", ItemStackArgumentType.itemStack())
                            .requires(source -> source.hasPermissionLevel(4))
                            .executes(QuickSpyglasserCommand::executeSetQSItemId)))));
            dispatcher.register(CommandManager.literal("qs").redirect(node));
        });
    }

    private static Integer executeSetQSItemId(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ItemStackArgument item = ItemStackArgumentType.getItemStackArgument(context, "item");
        ServerCommandSource source = context.getSource();

        QuickSpyglasser.getInstance().setQSItem(item.getItem());
        QuickSpyglasser.CONFIG.getConfig().quickSpyglassItemId = Registry.ITEM.getId(item.getItem()).toString();
        QuickSpyglasser.CONFIG.save();
        QuickSpyglasserNetwork.syncRequiredItem(source.getServer());

        source.sendFeedback(new TranslatableText("commands.quickspyglasser.config.success",
                "quickSpyglassItemId", item.createStack(1, false).toHoverableText()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static Integer sendCurrentQSItem(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(new TranslatableText("commands.quickspyglasser.config.queryItem",
                "quickSpyglassItemId", QuickSpyglasser.CONFIG.getConfig().quickSpyglassItemId,
                new ItemStack(QuickSpyglasser.getInstance().getQSItem(), 1).toHoverableText()), false);
        return Command.SINGLE_SUCCESS;
    }

//    @Environment(EnvType.CLIENT)
//    public static void initClient() {
//        var node = ClientCommandManager.DISPATCHER.register(
//            ClientCommandManager.literal("quickspyglasserclient")
//                .then(ClientCommandManager.literal("config")
//                    .then(ClientCommandManager.literal("showSpyglassOverlay")
//                        .then(ClientCommandManager.argument("value", BoolArgumentType.bool())
//                            .executes(context -> executeSetShowSpyglassOverlay(context.getSource(),
//                                    BoolArgumentType.getBool(context, "value")))))
//                    .then(ClientCommandManager.literal("playSpyglassSound")
//                        .then(ClientCommandManager.argument("value", BoolArgumentType.bool())
//                            .executes(context -> executeSetPlaySpyglassSound(context.getSource(),
//                                    BoolArgumentType.getBool(context, "value")))))
//                    .then(ClientCommandManager.literal("cinematicModeZoom")
//                        .then(ClientCommandManager.argument("value", BoolArgumentType.bool())
//                            .executes(context -> executeSetCinematicModeZoom(context.getSource(),
//                                    BoolArgumentType.getBool(context, "value")))))
//                    .then(ClientCommandManager.literal("mouseSensitivity")
//                        .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(0, 800))
//                            .executes(context -> executeSetMouseSensitivity(context.getSource(),
//                                    IntegerArgumentType.getInteger(context, "value")))))));
//        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("qsc").redirect(node));
//    }
//
//    @Environment(EnvType.CLIENT)
//    private static int executeSetShowSpyglassOverlay(FabricClientCommandSource source, Boolean value) throws CommandSyntaxException {
//        QuickSpyglasserClient.CONFIG.getConfig().showSpyglassOverlay = value;
//        QuickSpyglasserClient.CONFIG.save();
//        source.sendFeedback(new TranslatableText("commands.quickspyglasser.config.success", "showSpyglassOverlay", value));
//        return Command.SINGLE_SUCCESS;
//    }
//
//    @Environment(EnvType.CLIENT)
//    private static int executeSetPlaySpyglassSound(FabricClientCommandSource source, Boolean value) throws CommandSyntaxException {
//        QuickSpyglasserClient.CONFIG.getConfig().playSpyglassSound = value;
//        QuickSpyglasserClient.CONFIG.save();
//        source.sendFeedback(new TranslatableText("commands.quickspyglasser.config.success", "playSpyglassSound", value));
//        return Command.SINGLE_SUCCESS;
//    }
//
//    @Environment(EnvType.CLIENT)
//    private static int executeSetCinematicModeZoom(FabricClientCommandSource source, Boolean value) throws CommandSyntaxException {
//        QuickSpyglasserClient.CONFIG.getConfig().cinematicModeZoom = value;
//        QuickSpyglasserClient.CONFIG.save();
//        source.sendFeedback(new TranslatableText("commands.quickspyglasser.config.success", "cinematicModeZoom", value));
//        return Command.SINGLE_SUCCESS;
//    }
//
//    @Environment(EnvType.CLIENT)
//    private static int executeSetMouseSensitivity(FabricClientCommandSource source, Integer value) throws CommandSyntaxException {
//        QuickSpyglasserClient.CONFIG.getConfig().mouseSensitivity = value;
//        QuickSpyglasserClient.CONFIG.save();
//        source.sendFeedback(new TranslatableText("commands.quickspyglasser.config.success", "mouseSensitivity", value));
//        return Command.SINGLE_SUCCESS;
//    }
}
