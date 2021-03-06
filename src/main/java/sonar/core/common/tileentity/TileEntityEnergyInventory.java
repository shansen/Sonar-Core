package sonar.core.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import sonar.core.api.SonarAPI;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.inventory.SonarInventory;

import java.util.List;

public class TileEntityEnergyInventory extends TileEntityEnergy implements IInventory {

	public void discharge(int id) {		
		SonarAPI.getEnergyHelper().dischargeItem(slots().get(id), this, maxTransfer != 0 ? Math.min(maxTransfer, getStorage().getMaxExtract()) : getStorage().getMaxExtract());
	}

	public void charge(int id) {
		SonarAPI.getEnergyHelper().chargeItem(slots().get(id), this, maxTransfer != 0 ? Math.min(maxTransfer, getStorage().getMaxExtract()) : getStorage().getMaxExtract());
	}

	public SonarInventory inv;

	public SonarInventory inv() {
		return inv;
	}
	
    @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability || super.hasCapability(capability, facing);
	}

    @Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capability) {
			return (T) inv.getItemHandler(facing);
		}
		return super.getCapability(capability, facing);
	}
	
	public List<ItemStack> slots() {
		return inv.slots;
	}

    @Override
	public void readData(NBTTagCompound nbt, SyncType type) {
		super.readData(nbt, type);
		inv().readData(nbt, type);
	}

    @Override
	public NBTTagCompound writeData(NBTTagCompound nbt, SyncType type) {
		super.writeData(nbt, type);
		inv().writeData(nbt, type);
		return nbt;
	}

    @Override
	public int getSizeInventory() {
		return inv().getSizeInventory();
	}

    @Override
	public ItemStack getStackInSlot(int slot) {
		return inv().getStackInSlot(slot);
	}

    @Override
	public ItemStack decrStackSize(int slot, int var2) {
		return inv().decrStackSize(slot, var2);
	}

    @Override
	public ItemStack removeStackFromSlot(int slot) {
		return inv().getStackInSlot(slot);
	}

    @Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv().setInventorySlotContents(i, itemstack);
	}

    @Override
	public int getInventoryStackLimit() {
		return inv().getInventoryStackLimit();
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

    @Override
	public void openInventory(EntityPlayer player) {
		inv().openInventory(player);
	}

    @Override
	public void closeInventory(EntityPlayer player) {
		inv().closeInventory(player);
	}

    @Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return inv().isItemValidForSlot(slot, stack);
	}

    @Override
	public String getName() {
        return this.blockType == null ? "Sonar Inventory" : this.blockType.getLocalizedName();
	}

    @Override
	public boolean hasCustomName() {
		return false;
	}

    @Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(this.blockType.getLocalizedName());
	}

    @Override
	public int getField(int id) {
		return inv().getField(id);
	}

    @Override
	public void setField(int id, int value) {
		inv().setField(id, value);
	}

    @Override
	public int getFieldCount() {
		return inv().getFieldCount();
	}

    @Override
	public void clear() {
		inv().clear();
	}

	@Override
	public boolean isEmpty() {
		return inv().isEmpty();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return inv().isUsableByPlayer(player);
	}
}
