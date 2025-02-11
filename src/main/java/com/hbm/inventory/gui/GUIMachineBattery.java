package com.hbm.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.storage.TileEntityMachineBattery;
import com.hbm.util.BobMathUtil;

public class GUIMachineBattery extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_battery.png");
	private TileEntityMachineBattery battery;

	public GUIMachineBattery(InventoryPlayer invPlayer, TileEntityMachineBattery tedf) {
		super(new ContainerMachineBattery(invPlayer, tedf));
		battery = tedf;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 52, 52, battery.power, battery.getMaxPower());

		long delta = battery.log[19] - battery.log[0];
		String deltaText = BobMathUtil.getShortNumber(Math.abs(delta)) + "HE/s";

		if(delta > 0)
			deltaText = EnumChatFormatting.GREEN + "+" + deltaText;
		else if(delta < 0)
			deltaText = EnumChatFormatting.RED + "-" + deltaText;
		else
			deltaText = EnumChatFormatting.YELLOW + "+" + deltaText;

		String[] info = { BobMathUtil.getShortNumber(battery.power) + "/" + BobMathUtil.getShortNumber(battery.getMaxPower()) + "HE", deltaText };

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 69 - 52, 52, 52, mouseX, mouseY, info);

		String[] text = new String[] { "Click the buttons on the right", "to change battery behavior for", "when redstone is or isn't applied." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		if(battery.childLock) {
			String[] childLock = new String[] {
					EnumChatFormatting.RED + "Child Safety Lock for Buffer Mode",
					EnumChatFormatting.ITALIC + "What is Buffer Mode?",
					"Buffer Mode simply combines the input and output modes",
					"of the battery. " + EnumChatFormatting.RED + "NO, DON'T STOP READING YET.",
					"It absolutely does no more than that. It's not an omniscient",
					"load-balancer that somehow knows where you want to direct most of",
					"your energy to. Batteries - obviously - still receive energy when in",
					"Buffer Mode, which means that combining multiple batteries in Buffer",
					"Mode will cause them to constantly send energy back and forth,",
					"with only a small share going out to whatever it is you want powered.",
					"This can be solved easily by either only using Buffer Mode when",
					"actually necessary or by switching to another mode if required.",
					"Diodes may also help curb the \"ping-ponging\" of energy.",
					"",
					EnumChatFormatting.ITALIC + "What is Buffer Mode not?",
					"Something to use for every single battery because \"I want to have",
					"batteries send and receive anyway\". Rule of thumb: Use your brain,",
					"use diodes, actually think about how load distribution should work in",
					"your power grid."
			};
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 152, guiTop + 35, 16, 16, guiLeft - 80, guiTop, childLock);
		}
	}

	protected void mouseClicked(int x, int y, int i) {
		super.mouseClicked(x, y, i);

		if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 16 < y && guiTop + 16 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(battery.xCoord, battery.yCoord, battery.zCoord, 0, 0));
		}

		if(guiLeft + 133 <= x && guiLeft + 133 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(battery.xCoord, battery.yCoord, battery.zCoord, 0, 1));
		}

		if(guiLeft + 152 <= x && guiLeft + 152 + 16 > x && guiTop + 35 < y && guiTop + 35 + 16 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(battery.xCoord, battery.yCoord, battery.zCoord, 0, 2));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.battery.hasCustomInventoryName() ? this.battery.getInventoryName() : I18n.format(this.battery.getInventoryName());
		name += (" (" + this.battery.power + " HE)");

		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(battery.power > 0) {
			int i = (int) battery.getPowerRemainingScaled(52);
			drawTexturedModalRect(guiLeft + 62, guiTop + 69 - i, 176, 52 - i, 52, i);
		}

		int i = battery.redLow;
		drawTexturedModalRect(guiLeft + 133, guiTop + 16, 176, 52 + i * 18, 18, 18);

		int j = battery.redHigh;
		drawTexturedModalRect(guiLeft + 133, guiTop + 52, 176, 52 + j * 18, 18, 18);
		
		if(!battery.childLock)
			drawTexturedModalRect(guiLeft + 152, guiTop + 35, 176, 124, 16, 16);

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
	}
}
