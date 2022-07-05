package com.hijackster99.ancientrelics.core;

import java.util.List;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Util {
	
	public static VoxelShape rotateAABB(Direction axis, int dir, VoxelShape shape)
	{
		if(dir == 0) return shape;
		
		AABB aabb = shape.bounds();
		AABB newAabb;
		
		double minX = aabb.minX - 0.5, minY = aabb.minY - 0.5, minZ = aabb.minZ - 0.5, maxX = aabb.maxX - 0.5, maxY = aabb.maxY - 0.5, maxZ = aabb.maxZ - 0.5;
		
		if(axis == Direction.DOWN || axis == Direction.SOUTH || axis == Direction.WEST) dir *= -1;
		
		if(axis == Direction.UP || axis == Direction.DOWN)
		{
			if(dir == 1) {
				newAabb = new AABB(minZ + 0.5, minY + 0.5, -minX + 0.5, maxZ + 0.5, maxY + 0.5, -maxX + 0.5);
			}else if(dir == -1) {
				newAabb = new AABB(-minZ + 0.5, minY + 0.5, minX + 0.5, -maxZ + 0.5, maxY + 0.5, maxX + 0.5);
			}else {
				newAabb = new AABB(-minX + 0.5, minY + 0.5, -minZ + 0.5, -maxX + 0.5, maxY + 0.5, -maxZ + 0.5);
			}
		}else if(axis == Direction.NORTH || axis == Direction.SOUTH)
		{
			if(dir == 1) {
				newAabb = new AABB(minX + 0.5, -minZ + 0.5, minY + 0.5, maxX + 0.5, -maxZ + 0.5, maxY + 0.5);
			}else if(dir == -1) {
				newAabb = new AABB(minX + 0.5, minZ + 0.5, -minY + 0.5, maxX + 0.5, maxZ + 0.5, -maxY + 0.5);
			}else {
				newAabb = new AABB(minX + 0.5, -minY + 0.5, -minZ + 0.5, maxX + 0.5, -maxY + 0.5, -maxZ + 0.5);
			}
		}else 
		{
			if(dir == 1) {
				newAabb = new AABB(-minY + 0.5, minX + 0.5, minZ + 0.5, -maxY + 0.5, maxX + 0.5, maxZ + 0.5);
			}else if(dir == -1) {
				newAabb = new AABB(minY + 0.5, -minX + 0.5, minZ + 0.5, maxY + 0.5, -maxX + 0.5, maxZ + 0.5);
			}else {
				newAabb = new AABB(-minX + 0.5, -minY + 0.5, minZ + 0.5, -maxX + 0.5, -maxY + 0.5, maxZ + 0.5);
			}
		}
		
		return Shapes.create(newAabb);
	}
	
	public static VoxelShape rotateAABB(Direction from, Direction to, VoxelShape shape)
	{
		if(from == Direction.NORTH)
		{
			if(to == Direction.NORTH)
			{
				return shape;
			}else if(to == Direction.SOUTH)
			{
				return rotateAABB(Direction.UP, 2, shape);
			}else if(to == Direction.EAST)
			{
				return rotateAABB(Direction.UP, 1, shape);
			}else if(to == Direction.WEST)
			{
				return rotateAABB(Direction.UP, -1, shape);
			}else if(to == Direction.UP)
			{
				return rotateAABB(Direction.EAST, -1, shape);
			}else if(to == Direction.DOWN)
			{
				return rotateAABB(Direction.EAST, 1, shape);
			}
		}else if(from == Direction.SOUTH)
		{
			if(to == Direction.NORTH)
			{
				return shape;
			}else if(to == Direction.SOUTH)
			{
				return rotateAABB(Direction.UP, 1, shape);
			}else if(to == Direction.EAST)
			{
				return rotateAABB(Direction.UP, -1, shape);
			}else if(to == Direction.WEST)
			{
				return rotateAABB(Direction.UP, 1, shape);
			}else if(to == Direction.UP)
			{
				return rotateAABB(Direction.EAST, 1, shape);
			}else if(to == Direction.DOWN)
			{
				return rotateAABB(Direction.EAST, -1, shape);
			}
		}else if(from == Direction.EAST)
		{
			if(to == Direction.NORTH)
			{
				return rotateAABB(Direction.UP, -1, shape);
			}else if(to == Direction.SOUTH)
			{
				return rotateAABB(Direction.UP, 1, shape);
			}else if(to == Direction.EAST)
			{
				return shape;
			}else if(to == Direction.WEST)
			{
				return rotateAABB(Direction.UP, 2, shape);
			}else if(to == Direction.UP)
			{
				return rotateAABB(Direction.NORTH, 1, shape);
			}else if(to == Direction.DOWN)
			{
				return rotateAABB(Direction.NORTH, -1, shape);
			}
		}else if(from == Direction.WEST)
		{
			if(to == Direction.NORTH)
			{
				return rotateAABB(Direction.UP, 1, shape);
			}else if(to == Direction.SOUTH)
			{
				return rotateAABB(Direction.UP, -1, shape);
			}else if(to == Direction.EAST)
			{
				return rotateAABB(Direction.UP, 2, shape);
			}else if(to == Direction.WEST)
			{
				return shape;
			}else if(to == Direction.UP)
			{
				return rotateAABB(Direction.NORTH, -1, shape);
			}else if(to == Direction.DOWN)
			{
				return rotateAABB(Direction.NORTH, 1, shape);
			}
		}else if(from == Direction.UP)
		{
			if(to == Direction.NORTH)
			{
				return rotateAABB(Direction.EAST, 1, shape);
			}else if(to == Direction.SOUTH)
			{
				return rotateAABB(Direction.EAST, -1, shape);
			}else if(to == Direction.EAST)
			{
				return rotateAABB(Direction.NORTH, -1, shape);
			}else if(to == Direction.WEST)
			{
				return rotateAABB(Direction.NORTH, 1, shape);
			}else if(to == Direction.UP)
			{
				return shape;
			}else if(to == Direction.DOWN)
			{
				return rotateAABB(Direction.NORTH, 2, shape);
			}
		}else if(from == Direction.DOWN)
		{
			if(to == Direction.NORTH)
			{
				return rotateAABB(Direction.EAST, -1, shape);
			}else if(to == Direction.SOUTH)
			{
				return rotateAABB(Direction.EAST, 1, shape);
			}else if(to == Direction.EAST)
			{
				return rotateAABB(Direction.NORTH, 1, shape);
			}else if(to == Direction.WEST)
			{
				return rotateAABB(Direction.NORTH, -1, shape);
			}else if(to == Direction.UP)
			{
				return rotateAABB(Direction.NORTH, 2, shape);
			}else if(to == Direction.DOWN)
			{
				return shape;
			}
		}
		return shape;
	}
	
	public static <T> boolean containsAny(List<T> l1, List<T> l2) {
		for(T item : l2) {
			if(l1.contains(item)) return true;
		}
		return false;
	}
	
}
