package com.hbm.handler.guncfg;

import java.util.ArrayList;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class Gun50AEFactory {
	
	public static GunConfiguration getBaseConfig() {
		
		GunConfiguration config = new GunConfiguration();
		
		config.rateOfFire = 10;
		config.roundsPerCycle = 1;
		config.gunMode = GunConfiguration.MODE_NORMAL;
		config.firingMode = GunConfiguration.FIRE_MANUAL;
		config.reloadDuration = 10;
		config.firingDuration = 0;
		config.ammoCap = 7;
		config.reloadType = GunConfiguration.RELOAD_FULL;
		config.allowsInfinity = true;
		config.crosshair = Crosshair.L_CLASSIC;
		config.reloadSound = GunConfiguration.RSOUND_REVOLVER;
		config.firingSound = "hbm:weapon.deagleShoot";
		config.reloadSoundEnd = false;
		
		return config;
	}
	
	public static GunConfiguration getDeagleConfig() {
		
		GunConfiguration config = getBaseConfig();
		
		config.durability = 2500;
		
		config.name = "IMI Desert Eagle";
		config.manufacturer = "Magnum Research / Israel Military Industries";
		
		config.hasSights = true;
		config.config = new ArrayList<Integer>();
		config.config.add(BulletConfigSyncingUtil.AE50_NORMAL);
		config.config.add(BulletConfigSyncingUtil.AE50_AP);
		config.config.add(BulletConfigSyncingUtil.AE50_DU);
		config.config.add(BulletConfigSyncingUtil.AE50_STAR);
		config.config.add(BulletConfigSyncingUtil.CHL_AE50);
		
		return config;
	}

	static float inaccuracy = 0.0005F;
	public static BulletConfiguration get50AEConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = ModItems.ammo_50ae;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 28;
		bullet.dmgMax = 32;
		
		return bullet;
	}

	public static BulletConfiguration get50APConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = ModItems.ammo_50ae_ap;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 30;
		bullet.dmgMax = 36;
		bullet.leadChance = 10;
		bullet.wear = 15;
		
		return bullet;
	}

	public static BulletConfiguration get50DUConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = ModItems.ammo_50ae_du;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 38;
		bullet.dmgMax = 46;
		bullet.leadChance = 50;
		bullet.wear = 25;
		
		return bullet;
	}

	public static BulletConfiguration get50StarConfig() {
		
		BulletConfiguration bullet = BulletConfigFactory.standardPistolConfig();
		
		bullet.ammo = ModItems.ammo_50ae_star;
		bullet.spread *= inaccuracy;
		bullet.dmgMin = 52;
		bullet.dmgMax = 60;
		bullet.leadChance = 100;
		bullet.wear = 25;
		
		return bullet;
	}

}
