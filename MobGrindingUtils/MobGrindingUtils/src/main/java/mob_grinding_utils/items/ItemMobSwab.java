package mob_grinding_utils.items;

import java.util.List;

import mob_grinding_utils.MobGrindingUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMobSwab extends Item {

	public ItemMobSwab() {
		super();
		setMaxStackSize(1);
		setCreativeTab(MobGrindingUtils.TAB);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag) {
		if (!stack.hasTagCompound())
			list.add(TextFormatting.YELLOW + "Use on mobs to collect 'DNA'.");
		else if (stack.hasTagCompound() && stack.getTagCompound().hasKey("mguMobName")) {
			list.add(TextFormatting.YELLOW + "Craft with a Bucket of XP and Seeds.");
			list.add(TextFormatting.GREEN + "Contains: " + stack.getTagCompound().getTag("mguMobName") + " 'DNA'.");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		list.add(new ItemStack((item), 1, 0));
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
		if (target instanceof EntityLiving && !(target instanceof EntityPlayer) && getDamage(stack) == 0) {
			String mobName = EntityList.getEntityString(target);
			ItemStack stack2 = new ItemStack(MobGrindingUtils.MOB_SWAB, 1, 1);
			if (!stack2.hasTagCompound())
				stack2.setTagCompound(new NBTTagCompound());
			if (!stack2.getTagCompound().hasKey("mguMobName"))
				stack2.getTagCompound().setString("mguMobName", mobName);
			player.swingArm(hand);
			player.setHeldItem(hand, stack2);
			return true;
		} else {
			return super.itemInteractionForEntity(stack, player, target, hand);
		}
	}
}