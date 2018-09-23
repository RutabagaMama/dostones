package com.rutabagamama.dostones.items;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class HomeStone extends ItemBase {

	public HomeStone(String name) {
		super(name);

	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(player.dimension == 0) {

			BlockPos chunkCoords = player.getBedLocation(worldIn.provider.getDimension());

			if (chunkCoords == null)
				chunkCoords = worldIn.getSpawnPoint();

			player.rotationPitch = 0.0F;
			player.rotationYaw = 0.0F;
			player.setPositionAndUpdate(chunkCoords.getX(), chunkCoords.getY() + 0.1D, chunkCoords.getZ());

			while (!worldIn.getCollisionBoxes(player, player.getEntityBoundingBox()).isEmpty()) {
				player.setPositionAndUpdate(player.posX, player.posY + 1.0D, player.posZ);
			}

			player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);

			if (worldIn.isRemote) {

				ITextComponent welcomeMessage = player.getDisplayName();
				welcomeMessage.appendText(", welcome home!");
				player.sendMessage(welcomeMessage);
			}

			return super.onItemUse(player, worldIn, chunkCoords, hand, facing, hitX, hitY, hitZ);
		} else {
			if (worldIn.isRemote) {

				ITextComponent errorMessage = player.getDisplayName();
				errorMessage.appendText(", you can't get there from here.");
				player.sendMessage(errorMessage);
			}
			return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}
	}

}
