package fr.mineral.Utils.Save;

import fr.mineral.mineralcontest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileToGame {

    public boolean readFile(String worldName) throws IOException {

        try {
            mineralcontest plugin = mineralcontest.plugin;
            InputStream worldStream = new FileInputStream(plugin.getDataFolder() + File.separator + MapFileHandler.biome_data_folderPath + File.separator + worldName + ".json");
            JSONTokener tokener = new JSONTokener(worldStream);
            JSONObject monde = new JSONObject(tokener).getJSONObject(worldName);

            setHousesLocation(monde.getJSONObject("teams"));
            setDoors(monde.getJSONObject("teams"));
            setTeamChestLocation(monde.getJSONObject("teams"));
            setArenaLocation(monde.getJSONObject("arene"));

            for(Player online : mineralcontest.plugin.pluginWorld.getPlayers()) {
                try {
                    //online.sendMessage(mineralcontest.prefixGlobal + "Configuration du monde chargée avec succès");
                    online.teleport(mineralcontest.plugin.getGame().getArene().getDeathZone().getSpawnLocation());
                }catch(Exception e) {
                    mineralcontest.broadcastMessage(mineralcontest.prefixErreur + "Error while loading world");
                    mineralcontest.broadcastMessage(mineralcontest.prefixErreur + "This error usually happens when you reload the plugin. please dont, " + ChatColor.RED + "restart server instead");
                    e.printStackTrace();
                }

            }

            mineralcontest.plugin.getGame().isGameInitialized = true;
        }catch (Exception e) {
            mineralcontest.broadcastMessage(mineralcontest.prefixErreur + "Error while loading world");
            mineralcontest.broadcastMessage(mineralcontest.prefixErreur + "This error usually happens when you reload the plugin. please dont, " + ChatColor.RED + "restart server instead");
            e.printStackTrace();
        }


        return true;

    }

    public void setDoors(JSONObject teams) {
        JSONArray portes;

        // Team Rouge
        portes = teams.getJSONObject("rouge").getJSONArray("porte");
        for(int i = 0; i < portes.length(); i++) {
            Location emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
            JSONObject objet = portes.getJSONObject(i);
            emplacementCoordonne.setX(objet.getDouble("x"));
            emplacementCoordonne.setY(objet.getDouble("y"));
            emplacementCoordonne.setZ(objet.getDouble("z"));
            mineralcontest.plugin.getGame().getRedHouse().getPorte().addToDoor(emplacementCoordonne.getBlock());

        }

        // Team Jaune
        portes = teams.getJSONObject("jaune").getJSONArray("porte");
        for(int i = 0; i < portes.length(); i++) {
            Location emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
            JSONObject objet = portes.getJSONObject(i);
            emplacementCoordonne.setX(objet.getDouble("x"));
            emplacementCoordonne.setY(objet.getDouble("y"));
            emplacementCoordonne.setZ(objet.getDouble("z"));


            mineralcontest.plugin.getGame().getYellowHouse().getPorte().addToDoor(emplacementCoordonne.getBlock());

        }

        // Team Bleu
        portes = teams.getJSONObject("bleu").getJSONArray("porte");
        for(int i = 0; i < portes.length(); i++) {
            Location emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
            JSONObject objet = portes.getJSONObject(i);
            emplacementCoordonne.setX(objet.getDouble("x"));
            emplacementCoordonne.setY(objet.getDouble("y"));
            emplacementCoordonne.setZ(objet.getDouble("z"));
            mineralcontest.plugin.getGame().getBlueHouse().getPorte().addToDoor(emplacementCoordonne.getBlock());

        }
    }

    public void setHousesLocation(JSONObject teams) {
        Location emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);

        // On récupère les coordonnées du spawn rouge
        emplacementCoordonne.setX(teams.getJSONObject("rouge").getJSONObject("spawn").getDouble("x"));
        emplacementCoordonne.setY(teams.getJSONObject("rouge").getJSONObject("spawn").getDouble("y"));
        emplacementCoordonne.setZ(teams.getJSONObject("rouge").getJSONObject("spawn").getDouble("z"));
        mineralcontest.plugin.getGame().getRedHouse().setHouseLocation(emplacementCoordonne);


        // On récupère les coordonnées du spawn bleu
        emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
        emplacementCoordonne.setX(teams.getJSONObject("bleu").getJSONObject("spawn").getDouble("x"));
        emplacementCoordonne.setY(teams.getJSONObject("bleu").getJSONObject("spawn").getDouble("y"));
        emplacementCoordonne.setZ(teams.getJSONObject("bleu").getJSONObject("spawn").getDouble("z"));
        mineralcontest.plugin.getGame().getBlueHouse().setHouseLocation(emplacementCoordonne);


        // On récupère les coordonnées du spawn rouge
        emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
        emplacementCoordonne.setX(teams.getJSONObject("jaune").getJSONObject("spawn").getDouble("x"));
        emplacementCoordonne.setY(teams.getJSONObject("jaune").getJSONObject("spawn").getDouble("y"));
        emplacementCoordonne.setZ(teams.getJSONObject("jaune").getJSONObject("spawn").getDouble("z"));

        mineralcontest.plugin.getGame().getYellowHouse().setHouseLocation(emplacementCoordonne);
    }

    public void setTeamChestLocation(JSONObject teams) {
        Location emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);

        // On récupère les coordonnées du spawn rouge
        emplacementCoordonne.setX(teams.getJSONObject("rouge").getJSONObject("coffre").getDouble("x"));
        emplacementCoordonne.setY(teams.getJSONObject("rouge").getJSONObject("coffre").getDouble("y"));
        emplacementCoordonne.setZ(teams.getJSONObject("rouge").getJSONObject("coffre").getDouble("z"));
        mineralcontest.plugin.getGame().getRedHouse().setCoffreEquipe(emplacementCoordonne);

        emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);

        // On récupère les coordonnées du spawn bleu
        emplacementCoordonne.setX(teams.getJSONObject("bleu").getJSONObject("coffre").getDouble("x"));
        emplacementCoordonne.setY(teams.getJSONObject("bleu").getJSONObject("coffre").getDouble("y"));
        emplacementCoordonne.setZ(teams.getJSONObject("bleu").getJSONObject("coffre").getDouble("z"));
        mineralcontest.plugin.getGame().getBlueHouse().setCoffreEquipe(emplacementCoordonne);

        emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
        // On récupère les coordonnées du spawn rouge
        emplacementCoordonne.setX(teams.getJSONObject("jaune").getJSONObject("coffre").getDouble("x"));
        emplacementCoordonne.setY(teams.getJSONObject("jaune").getJSONObject("coffre").getDouble("y"));
        emplacementCoordonne.setZ(teams.getJSONObject("jaune").getJSONObject("coffre").getDouble("z"));

        mineralcontest.plugin.getGame().getYellowHouse().setCoffreEquipe(emplacementCoordonne);
    }

    public void setArenaLocation(JSONObject arene) {
        Location emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
        // On récupère le coffre
        emplacementCoordonne.setX(arene.getJSONObject("coffre").getDouble("x"));
        emplacementCoordonne.setY(arene.getJSONObject("coffre").getDouble("y"));
        emplacementCoordonne.setZ(arene.getJSONObject("coffre").getDouble("z"));
        mineralcontest.plugin.getGame().getArene().setCoffre(emplacementCoordonne);


        // On récupère le spawn
        emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
        emplacementCoordonne.setX(arene.getJSONObject("spawn").getDouble("x"));
        emplacementCoordonne.setY(arene.getJSONObject("spawn").getDouble("y"));
        emplacementCoordonne.setZ(arene.getJSONObject("spawn").getDouble("z"));
        mineralcontest.plugin.getGame().getArene().setTeleportSpawn(emplacementCoordonne);

        // Deathzone
        emplacementCoordonne = new Location(Bukkit.getServer().getWorld("World"), 0d, 0d, 0d);
        emplacementCoordonne.setX(arene.getJSONObject("deathZone").getDouble("x"));
        emplacementCoordonne.setY(arene.getJSONObject("deathZone").getDouble("y"));
        emplacementCoordonne.setZ(arene.getJSONObject("deathZone").getDouble("z"));
        mineralcontest.plugin.getGame().getArene().getDeathZone().setSpawnLocation(emplacementCoordonne);
    }
}
