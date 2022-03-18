package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PickupEvent extends TownListener {

	public PickupEvent(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void onPickUp(EntityPickupItemEvent e) {
		if(!(e.getEntity() instanceof Player player)) return;

		Chunk chunk = e.getItem().getLocation().getChunk();
		Flag flag = Flag.PICKUP;

		if(this.needCancel(chunk, player, flag)) {
			e.setCancelled(true);
		}
	}
}
