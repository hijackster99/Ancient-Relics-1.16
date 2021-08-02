package com.hijackster99.ancientrelics.core;

import java.util.List;

import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;

public class Util {
	
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		if(shape.toAabbs().size() > 1) throw new IllegalArgumentException();
		AxisAlignedBB aabb = shape.bounds();
		AxisAlignedBB aabbnew;
		
		Vector3d vec1 = new Vector3d(aabb.minX - 0.5, aabb.minY - 0.5, aabb.minZ - 0.5);
		Vector3d vec2 = new Vector3d(aabb.maxX - 0.5, aabb.maxY - 0.5, aabb.maxZ - 0.5);
		
		if((from == Direction.UP && to == Direction.NORTH) || (from == Direction.NORTH && to == Direction.DOWN) || (from == Direction.DOWN && to == Direction.SOUTH) || (from == Direction.SOUTH && to == Direction.UP)) {
			vec1 = vec1.zRot((float) Math.toRadians(90));
			vec2 = vec2.zRot((float) Math.toRadians(90));
		}else if((from == Direction.UP && to == Direction.SOUTH) || (from == Direction.SOUTH && to == Direction.DOWN) || (from == Direction.DOWN && to == Direction.NORTH) || (from == Direction.NORTH && to == Direction.UP)) {
			vec1 = vec1.zRot((float) Math.toRadians(270));
			vec2 = vec2.zRot((float) Math.toRadians(270));
		}else if((from == Direction.UP && to == Direction.EAST) || (from == Direction.EAST && to == Direction.DOWN) || (from == Direction.DOWN && to == Direction.WEST) || (from == Direction.WEST && to == Direction.UP)) {
			vec1 = vec1.xRot((float) Math.toRadians(90));
			vec2 = vec2.xRot((float) Math.toRadians(90));
		}else if((from == Direction.UP && to == Direction.WEST) || (from == Direction.WEST && to == Direction.DOWN) || (from == Direction.DOWN && to == Direction.EAST) || (from == Direction.EAST && to == Direction.UP)) {
			vec1 = vec1.xRot((float) Math.toRadians(270));
			vec2 = vec2.xRot((float) Math.toRadians(270));
		}else if((from == Direction.UP && to == Direction.DOWN) || (from == Direction.DOWN && to == Direction.UP)) {
			vec1 = vec1.zRot((float) Math.toRadians(180));
			vec2 = vec2.zRot((float) Math.toRadians(180));
		}else if((from == Direction.EAST && to == Direction.WEST) || (from == Direction.WEST && to == Direction.EAST) || (from == Direction.NORTH && to == Direction.SOUTH) || (from == Direction.SOUTH && to == Direction.NORTH)) {
			vec1 = vec1.yRot((float) Math.toRadians(180));
			vec2 = vec2.yRot((float) Math.toRadians(180));
		}else if((from == Direction.NORTH && to == Direction.EAST) || (from == Direction.EAST && to == Direction.SOUTH) || (from == Direction.SOUTH && to == Direction.WEST) || (from == Direction.WEST && to == Direction.NORTH)) {
			vec1 = vec1.yRot((float) Math.toRadians(90));
			vec2 = vec2.yRot((float) Math.toRadians(90));
		}else if((from == Direction.NORTH && to == Direction.WEST) || (from == Direction.WEST && to == Direction.SOUTH) || (from == Direction.SOUTH && to == Direction.EAST) || (from == Direction.EAST && to == Direction.NORTH)) {
			vec1 = vec1.yRot((float) Math.toRadians(270));
			vec2 = vec2.yRot((float) Math.toRadians(270));
		}
		
		aabbnew = new AxisAlignedBB(vec1.x + 0.5, vec1.y + 0.5, vec1.z + 0.5, vec2.x + 0.5, vec2.y + 0.5, vec2.z + 0.5);
		
		return VoxelShapes.create(aabbnew);
	}
	
	public static <T> boolean containsAny(List<T> l1, List<T> l2) {
		for(T item : l2) {
			if(l1.contains(item)) return true;
		}
		return false;
	}
	
}
