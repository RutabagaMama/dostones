package com.rutabagamama.dostones.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import java.lang.Iterable;
import java.util.ArrayList;

public class GrowStone extends ItemBase {

	public GrowStone(String name) {
		super(name);
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		int itemRange = 3;
		
		Iterable<BlockPos> blocks = getAreaBlocks(worldIn, player, pos, itemRange);
        for (BlockPos areaPos : blocks) { 
            growBlock(player, worldIn, areaPos, hand, facing, hitX, hitY, hitZ);
        }
        return EnumActionResult.SUCCESS;
    }
	
	public EnumActionResult growBlock(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack handStack = player.getHeldItem(hand);
        if (!player.canPlayerEdit(pos.offset(facing), facing, handStack)) return EnumActionResult.FAIL;
        
     // find out where the player is hitting the block
        IBlockState state = worldIn.getBlockState(pos);
        
        Block bonemeal_substitute = new Block(Material.SAND);
        
        ItemStack itemStack = new ItemStack(bonemeal_substitute);

        if(state.getMaterial() != Material.AIR) {
        	
        	if (ItemDye.applyBonemeal(itemStack, worldIn, pos)) {
        		
        		 if (!worldIn.isRemote) {
                     worldIn.playEvent(2005, pos, 0);
        		 }
            }
        	return EnumActionResult.SUCCESS;
        } else {
        	return EnumActionResult.PASS;
        }
        
	}
	
    // a square horizonal surface area with origin in the center with a side length of 1 + (2 * n)
    public Iterable<BlockPos> getAreaBlocks(World world, EntityPlayer player, BlockPos origin, int distance) {
       
        // find out where the player is hitting the block
        IBlockState state = world.getBlockState(origin);

        if(state.getMaterial() == Material.AIR) {
          // what are you DOING?
          return new ArrayList<BlockPos>();
        }
        
        BlockPos start = origin;
        
        BlockPos from = origin.north(distance).west(distance);
        BlockPos to = origin.south(distance).east(distance);
        
        return BlockPos.getAllInBox(from, to);

      }

		
	
}
