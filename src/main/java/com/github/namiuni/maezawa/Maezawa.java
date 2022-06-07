package com.github.namiuni.maezawa;

import cloud.commandframework.CommandTree;
import cloud.commandframework.annotations.AnnotationParser;
import cloud.commandframework.arguments.parser.ParserParameters;
import cloud.commandframework.arguments.parser.StandardParameters;
import cloud.commandframework.bukkit.BukkitCommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Function;

public class Maezawa extends JavaPlugin {

    private static Economy economy = null;

    private BukkitCommandManager<CommandSender> commandManager;
    private AnnotationParser<CommandSender> annotationParser;

    @Override
    public void onEnable() {
        // API hook
        if (!setupEconomy() ) {
            getLogger().info("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Commands
        loadCommands();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            return false;
        }
        this.economy = economyProvider.getProvider();
        return this.economy != null;
    }

    private void loadCommands() {
        final Function<CommandTree<CommandSender>, CommandExecutionCoordinator<CommandSender>> executionCoordinatorFunction =
                AsynchronousCommandExecutionCoordinator.<CommandSender>newBuilder().build();
        final Function<CommandSender, CommandSender> mapperFunction = Function.identity();
        try {
            this.commandManager = new PaperCommandManager<>(
                    this,
                    executionCoordinatorFunction,
                    mapperFunction,
                    mapperFunction
            );
        } catch (final Exception e) {
            this.getLogger().severe("Failed to initialize the command this.manager");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (this.commandManager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            ((PaperCommandManager<CommandSender>) this.commandManager).registerAsynchronousCompletions();
        }

        final Function<ParserParameters, CommandMeta> commandMetaFunction = p ->
                CommandMeta.simple()
                        // This will allow you to decorate commands with descriptions
                        .with(CommandMeta.DESCRIPTION, p.get(StandardParameters.DESCRIPTION, "No description"))
                        .build();

        this.annotationParser = new AnnotationParser<>(
                this.commandManager,
                CommandSender.class,
                commandMetaFunction
        );

        this.annotationParser.parse(new MaezawaCommand());
    }

    public static Economy economy() {
        return economy;
    }
}
