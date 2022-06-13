package io.github.itstaylz.sakurabranch.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;

public class SkullUtils {

    public static ItemStack createSkull(String uuid, String url) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        PlayerProfile profile = Bukkit.createPlayerProfile(uuid);
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (meta != null)
            meta.setOwnerProfile(profile);
        skull.setItemMeta(meta);
        return skull;
    }
}
