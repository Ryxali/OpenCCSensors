package openccsensors.common.sensor;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import openccsensors.api.ISensorTier;
import openccsensors.common.util.InventoryUtils;

public abstract class TileSensor {

	
	public boolean isValidTarget(Object target) {
		return false;
	}
	
	public HashMap getDetails(TileEntity tile, Vec3 sensorPos) {

		HashMap response = new HashMap();
		HashMap position = new HashMap();

		position.put("X", tile.xCoord - sensorPos.xCoord);
		position.put("Y", tile.yCoord - sensorPos.yCoord);
		position.put("Z", tile.zCoord - sensorPos.zCoord);
		response.put("Position", position);
		
		ItemStack stack = new ItemStack(tile.getBlockType(), 1, tile.getBlockMetadata());
		
		response.put("Name", InventoryUtils.getNameForItemStack(stack));
		response.put("RawName", InventoryUtils.getRawNameForStack(stack));
		response.put("DamageValue", stack.getItemDamage());
		
		return response;
	}
	
	public HashMap getTargets(World world, Vec3 location, ISensorTier tier) {
		
		HashMap targets = new HashMap();
		int distance = (int) tier.getMultiplier();
		
		for (int x = -distance; x <= distance; x++) {
			for (int y = -distance; y <= distance; y++) {
				for (int z = -distance; z <= distance; z++) {

					int tileX = x + (int)location.xCoord;
					int tileY = y + (int)location.yCoord;
					int tileZ = z + (int)location.zCoord;

					String name = String.format("%s,%s,%s", x, y, z);

					TileEntity tile = world.getBlockTileEntity(tileX, tileY, tileZ);

					if (isValidTarget(tile)) {
						targets.put(name, tile);
					}

				}
			}
		}
		
		return targets;
		
	}
}
