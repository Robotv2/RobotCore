package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.impl.Town;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Optional;

public class EntityDamage extends TownListener {

	public EntityDamage(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player damager)) {
			return;
		}

		Entity ent = e.getEntity();
		Chunk chunk = ent.getLocation().getChunk();

		Optional<Town> optionalTown = getTownModule().getChunkManager().getTown(chunk);
		if (optionalTown.isEmpty()) return;

		Town town = optionalTown.get();
		if (town.isMember(damager)) return;
		
		boolean pve = Flag.PVE.isEnabledFor(town);
		boolean pvp = Flag.PVP.isEnabledFor(town);

		if(e.getEntity() instanceof Player) {
			if(!pvp) e.setCancelled(true);
		} else if(!pve) {
			e.setCancelled(true);
		}
	}
}
