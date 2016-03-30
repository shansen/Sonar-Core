package sonar.core.helpers;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sonar.core.integration.SonarLoader;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

/** helps with getting tiles, adding energy and checking stacks */
public class SonarHelper {

	/** @param tile Tile Entity you're checking from
	 * @param side side to find IEnergyHandler
	 * @return if there is an adjacent Energy Hander */
	/*
	public static boolean isAdjacentEnergyHandlerFromSide(TileEntity tile, int side) {
		TileEntity handler = getAdjacentTileEntity(tile, EnumFacing.getFront(side));
		return isEnergyHandlerFromSide(handler, EnumFacing.VALUES[side ^ 1]);
	}

	/*
	public static boolean isAdjacentEnergyHandlerFromSide(TileEntity tile, EnumFacing side) {
		TileEntity handler = getAdjacentTileEntity(tile, side);
		return isEnergyHandlerFromSide(handler, side.getOpposite());
	}

	/** @param tile Tile Entity you want to check
	 * @param from direction your adding from
	 * @return if Handler can connect */
	/*
	public static boolean isEnergyHandlerFromSide(TileEntity tile, EnumFacing from) {
		if (tile instanceof IEnergyProvider || tile instanceof IEnergyReceiver) {
			IEnergyConnection handler = (IEnergyConnection) tile;
			return handler.canConnectEnergy(from);
		}
		return false;
	}
	*/
	/** @param tile Tile Entity you're checking from
	 * @param side direction from Tile Entity your checking from
	 * @return adjacent Tile Entity */
	
	public static TileEntity getAdjacentTileEntity(TileEntity tile, EnumFacing side) {
		return tile.getWorld().getTileEntity(tile.getPos().offset(side));
	}

	public static Block getAdjacentBlock(World world, BlockPos pos, EnumFacing side) {
		return world.getBlockState(pos.offset(side)).getBlock();
	}
	/*
	/** @param tile Tile Entity you are checking
	 * @return if the Tile is an Energy Handler */
	/*
	public static boolean isEnergyHandler(TileEntity tile) {
		if (tile instanceof IEnergyHandler) {
			return true;
		}
		return false;
	}
	/** Add energy to an IEnergyReciever, internal distribution is left entirely to the IEnergyReciever.
	 * @param from Orientation the energy is received from.
	 * @param maxReceive Maximum amount of energy to receive.
	 * @param simulate If TRUE, the charge will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) received. */
	/*
	public static int pushEnergy(TileEntity tile, EnumFacing dir, int amount, boolean simulate) {
		if (tile instanceof IEnergyReceiver) {
			IEnergyReceiver handler = (IEnergyReceiver) tile;
			return handler.receiveEnergy(dir, amount, simulate);
		}
		return 0;

	}
	*/
	/** Remove energy from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
	 * @param from Orientation the energy is extracted from.
	 * @param maxExtract Maximum amount of energy to extract.
	 * @param simulate If TRUE, the extraction will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) extracted. */
	/*
	public static int pullEnergy(TileEntity tile, EnumFacing dir, int amount, boolean simulate) {
		if (tile instanceof IEnergyProvider) {
			IEnergyProvider handler = (IEnergyProvider) tile;
			return handler.extractEnergy(dir, amount, simulate);
		}
		return 0;

	}
	*/
	/** checks if a tile implements IWrench and IDropTile and drops it accordingly */
	public static void dropTile(EntityPlayer player, Block block, World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (SonarLoader.calculatorLoaded() && block == GameRegistry.findBlock("Calculator", "ConductorMastBlock")) {
			if (world.getBlockState(pos.offset(EnumFacing.DOWN, 1)).getBlock() == GameRegistry.findBlock("Calculator", "ConductorMast")) {
				block.harvestBlock(world, player, pos.offset(EnumFacing.DOWN, 1), world.getBlockState(pos.offset(EnumFacing.DOWN, 1)), te);
			} else if (world.getBlockState(pos.offset(EnumFacing.DOWN, 2)).getBlock() == GameRegistry.findBlock("Calculator", "ConductorMast")) {
				block.harvestBlock(world, player, pos.offset(EnumFacing.DOWN, 2), world.getBlockState(pos.offset(EnumFacing.DOWN, 3)), te);
			} else if (world.getBlockState(pos.offset(EnumFacing.DOWN, 3)).getBlock() == GameRegistry.findBlock("Calculator", "ConductorMast")) {

				block.harvestBlock(world, player, pos.offset(EnumFacing.DOWN, 3), world.getBlockState(pos.offset(EnumFacing.DOWN, 3)), te);
			}
		} else {
			block.harvestBlock(world, player, pos, world.getBlockState(pos), te);
		}

	}

	public static Entity getNearestEntity(Class entityClass, TileEntity tile, int range) {

		AxisAlignedBB aabb = AxisAlignedBB.fromBounds(tile.getPos().getX() - range, tile.getPos().getY() - range, tile.getPos().getZ() - range, tile.getPos().getX() + range, tile.getPos().getY() + range, tile.getPos().getZ() + range);

		List<Entity> entities = tile.getWorld().getEntitiesWithinAABB(entityClass, aabb);
		Entity entity = null;
		double closest = Double.MAX_VALUE;
		for (int i = 0; i < entities.size(); i++) {
			Entity target = (Entity) entities.get(i);
			double d0 = tile.getPos().getX() - target.posX;
			double d1 = tile.getPos().getY() - target.posY;
			double d2 = tile.getPos().getZ() - target.posZ;
			double distance = d0 * d0 + d1 * d1 + d2 * d2;

			if (distance < closest) {
				entity = target;
				closest = distance;
			}

		}
		return entity;

	}

	public static EnumFacing getForward(int meta) {
		return EnumFacing.getFront(meta).rotateYCCW();
	}

	public static int getAngleFromMeta(int meta) {
		switch (meta) {
		case 2:
			return 180;
		case 3:
			return 0;
		case 4:
			return 90;
		case 5:
			return 270;
		}
		return 0;

	}

	public static int invertMetadata(int meta) {
		switch (meta) {
		case 0:
			return 0;
		case 1:
			return 5;
		case 2:
			return 4;
		case 3:
			return 3;
		case 4:
			return 2;
		case 5:
			return 1;
		default:
			return -1;
		}
	}

	public static ItemStack createStackedBlock(Block block, int meta) {
		if (block == null) {
			return null;
		}
		Item item = Item.getItemFromBlock(block);
		if (item == null) {
			return null;
		}
		int j = 0;
		if (item.getHasSubtypes()) {
			j = meta;
		}

		return new ItemStack(item, 1, j);
	}

	public static EnumFacing offsetFacing(EnumFacing facing, EnumFacing front) {
		if (facing.getAxis().getPlane() == EnumFacing.Plane.VERTICAL || front.getAxis().getPlane() == EnumFacing.Plane.VERTICAL) {
			return facing;
		} else {
			return facing.fromAngle(getRenderRotation(front) + getRenderRotation(facing)).getOpposite();
		}
	}

	public static int getRenderRotation(EnumFacing face) {
		switch (face) {
		case SOUTH:
			return 180;
		case WEST:
			return 270;
		case EAST:
			return 90;
		default:
			return 0;
		}
	}

	public static EnumFacing getHorizontal(EnumFacing dir) {
		if (dir == EnumFacing.NORTH) {
			return EnumFacing.EAST;
		}
		if (dir == EnumFacing.EAST) {
			return EnumFacing.SOUTH;
		}
		if (dir == EnumFacing.SOUTH) {
			return EnumFacing.WEST;
		}
		if (dir == EnumFacing.WEST) {
			return EnumFacing.NORTH;
		}
		return null;

	}
}