package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTurbofan extends TileEntitySpecialRenderer {

	public RenderTurbofan() {
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(270, 0F, 1F, 0F);
		
		switch(tileEntity.getBlockMetadata()) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.turbofan_tex);
		ResourceManager.turbofan.renderPart("Body");
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(((TileEntityMachineTurbofan) tileEntity).spin, 0, 0, -1);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.turbofan.renderPart("Blades");
		GL11.glPopMatrix();
		bindTexture(ResourceManager.turbofan_back_tex);
		ResourceManager.turbofan.renderPart("Afterburner");
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
