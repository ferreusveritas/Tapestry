package com.ferreusveritas.tapestry.client.model;

import java.lang.reflect.Field;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelBoxFace extends ModelBox {

	private static Field quadListField = null;

	private final int[] faceMap = { 2, 3, 4, 5, 1, 0 };

	private EnumFacing face = EnumFacing.EAST;

	static {
		quadListField = ReflectionHelper.findField(ModelBox.class, "field_78254_i", "quadList");
	}

	protected final TexturedQuad[] quadList;

	private static TexturedQuad[] getQuadList(ModelBoxFace obj) {
		try {
			return (TexturedQuad[]) quadListField.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	public ModelBoxFace(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta) {
		this(renderer, texU, texV, x, y, z, dx, dy, dz, delta, renderer.mirror);
	}

	public ModelBoxFace(ModelRenderer renderer, int texU, int texV, float x, float y, float z, int dx, int dy, int dz, float delta, boolean mirror) {
		super(renderer, texU, texV, x, y, z, dx, dy, dz, delta, mirror);
		quadList = getQuadList(this);
	}

	@SideOnly(Side.CLIENT)
	public void render(BufferBuilder renderer, float scale) {
		quadList[faceMap[face.ordinal()]].draw(renderer, scale);
	}

	public ModelBoxFace setFace(EnumFacing facing) {
		face = facing;
		return this;
	}
	
	public ModelBoxFace scale(float x, float y, float z) {
		
		for(TexturedQuad quad : quadList) {
			for(PositionTextureVertex pos : quad.vertexPositions) {
				Vec3d in = pos.vector3D;
				pos.vector3D = new Vec3d(in.x * x, in.y * y, in.z * z);
			}
		}
		
		return this;
	}
		
	public ModelBoxFace offset(float x, float y, float z) {
		
		for(TexturedQuad quad : quadList) {
			for(PositionTextureVertex pos : quad.vertexPositions) {
				Vec3d in = pos.vector3D;
				pos.vector3D = new Vec3d(in.x + x, in.y + y, in.z + z);
			}
		}
		
		return this;
	}
}
