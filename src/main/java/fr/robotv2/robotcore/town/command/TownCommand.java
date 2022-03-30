package fr.robotv2.robotcore.town.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Town;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@CommandAlias("town")
public class TownCommand extends BaseCommand {

    private final TownModule module;
    public TownCommand(TownModule module) {
        this.module = module;
    }

    @Subcommand("create")
    @CommandPermission("robotcore.town.command.create")
    @Syntax("<town's name>")
    public void onCreate(Player player, String townNAME) {

        if(module.getPlayerManager().isInTown(player)) {
            module.getTownMessage().sendPath(player, "in-town");
            return;
        }

        if(module.getTownManager().getTown(townNAME).isPresent()) {
            module.getTownMessage().sendPath(player, "name-already-taken");
            return;
        }

        module.getTownManager().createTown(UUID.randomUUID(), player.getUniqueId(), townNAME);
        module.getTownMessage().sendPath(player, "town-created");
    }

    @Subcommand("delete")
    @CommandPermission("robotcore.town.command.delete")
    public void onDelete(Player player) {
        Optional<Town> optionalTown = module.getPlayerManager().getTown(player);

        if(optionalTown.isEmpty()) {
            module.getTownMessage().sendPath(player, "not-in-town");
            return;
        }

        Town town = optionalTown.get();

        if(!town.isChef(player)) {
            module.getTownMessage().sendPath(player, "not-chef");
            return;
        }

        module.getDataHandler().getData().deleteTown(town.getTownUUID());
        module.getTownManager().unloadTown(town.getTownUUID(), false);
        module.getTownMessage().sendPath(player, "town-deleted");
    }

    @Subcommand("info")
    @CommandPermission("robotcore.town.command.info")
    public void onInfo(Player player) {
        Optional<Town> optionalTown = module.getPlayerManager().getTown(player);

        if(optionalTown.isEmpty()) {
            module.getTownMessage().sendPath(player, "not-in-town");
            return;
        }

        Town town = optionalTown.get();

        StringUtil.sendMessage(player, "&8&m&l--------", false);
        StringUtil.sendMessage(player, "&7Nom: &e" + town.getName(), false);
        StringUtil.sendMessage(player, "&7Chef: &e" + town.getChef().getName(), false);
        StringUtil.sendMessage(player, "&7Solde: &e" + town.getBank() + "$", false);
        StringUtil.sendMessage(player, "&8&m&l--------", false);
    }
}
