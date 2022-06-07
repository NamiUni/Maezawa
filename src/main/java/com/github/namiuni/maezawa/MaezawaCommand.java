package com.github.namiuni.maezawa;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

@CommandMethod("maezawa|mz")
public class MaezawaCommand {

    private Component prefix = Component.text("[Maezawa]")
            .color(NamedTextColor.GOLD)
            .append(Component.text(" "));

    @CommandMethod("give onlineplayers <amount>")
    @CommandPermission("maezawa.give")
    private void giveOnlinePlayers(
            CommandSender sender,
            @Argument("amount") double amount
    ) {
        Economy economy = Maezawa.economy();

        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player onlinePlayer : onlinePlayers) {
            economy.depositPlayer(onlinePlayer, amount);
        }

        Component completeMessage = Component.text("")
                .append(prefix)
                .append(Component.text("全てのオンラインプレイヤーに"))
                .append(Component.text(economy.format(amount), NamedTextColor.GOLD))
                .append(Component.text("送金しました"))
                .append(Component.text("(", NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(onlinePlayers.size(), NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("人)", NamedTextColor.LIGHT_PURPLE));

        sender.sendMessage(completeMessage);
    }

    @CommandMethod("give offlineplayers <amount>")
    @CommandPermission("maezawa.give")
    private void giveOfflinePlayers(
            CommandSender sender,
            @Argument("amount") double amount
    ) {
        Economy economy = Maezawa.economy();

        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            economy.depositPlayer(offlinePlayer, amount);
        }

        Component completeMessage = Component.text("")
                .append(prefix)
                .append(Component.text("全てのオフラインプレイヤーに"))
                .append(Component.text(economy.format(amount), NamedTextColor.GOLD))
                .append(Component.text("送金しました"))
                .append(Component.text("(", NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(offlinePlayers.length, NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("人)", NamedTextColor.LIGHT_PURPLE));

        sender.sendMessage(completeMessage);
    }

    @CommandMethod("take onlineplayers <amount>")
    @CommandPermission("maezawa.give")
    private void takeOnlinePlayers(
            CommandSender sender,
            @Argument("amount") double amount
    ) {
        Economy economy = Maezawa.economy();

        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player onlinePlayer : onlinePlayers) {
            economy.withdrawPlayer(onlinePlayer, amount);
        }

        Component completeMessage = Component.text("")
                .append(prefix)
                .append(Component.text("全てのオンラインプレイヤーから"))
                .append(Component.text(economy.format(amount), NamedTextColor.GOLD))
                .append(Component.text("徴収しました"))
                .append(Component.text("(", NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(onlinePlayers.size(), NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("人)", NamedTextColor.LIGHT_PURPLE));

        sender.sendMessage(completeMessage);
    }

    @CommandMethod("take offlineplayers <amount>")
    @CommandPermission("maezawa.give")
    private void takeOfflinePlayers(
            CommandSender sender,
            @Argument("amount") double amount
    ) {
        Economy economy = Maezawa.economy();

        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            economy.withdrawPlayer(offlinePlayer, amount);
        }

        Component completeMessage = Component.text("")
                .append(prefix)
                .append(Component.text("全てのオフラインプレイヤーから"))
                .append(Component.text(economy.format(amount), NamedTextColor.GOLD))
                .append(Component.text("徴収しました"))
                .append(Component.text("(", NamedTextColor.LIGHT_PURPLE))
                .append(Component.text(offlinePlayers.length, NamedTextColor.LIGHT_PURPLE))
                .append(Component.text("人)", NamedTextColor.LIGHT_PURPLE));

        sender.sendMessage(completeMessage);
    }

}
