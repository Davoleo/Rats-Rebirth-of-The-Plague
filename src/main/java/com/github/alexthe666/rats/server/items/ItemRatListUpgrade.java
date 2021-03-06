package com.github.alexthe666.rats.server.items;

import com.github.alexthe666.rats.RatsMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRatListUpgrade extends ItemRatUpgrade {

    private boolean whitelist;

    public ItemRatListUpgrade(String name, boolean whitelist) {
        this(name, 0, 0, whitelist);
    }

    public ItemRatListUpgrade(String name, int rarity, int textLength, boolean whitelist) {
        super(name, rarity, textLength);
        this.whitelist = whitelist;
        this.setMaxStackSize(1);
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack itemStackIn = player.getHeldItem(hand);
        if (!player.isSneaking()) {
            player.openGui(RatsMod.INSTANCE, 3, worldIn, 0, 0, 0);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
    }

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound nbttagcompound1 = stack.getTagCompound();

        if (nbttagcompound1 != null && nbttagcompound1.hasKey("Items", 9)) {
            NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(nbttagcompound1, nonnulllist);
            int i = 0;
            int j = 0;
            for (ItemStack itemstack : nonnulllist) {
                if (!itemstack.isEmpty()) {
                    ++j;
                    if (i <= 4) {
                        ++i;
                        tooltip.add(String.format("%s", itemstack.getDisplayName()));
                    }
                }
            }
            if (j - i > 0) {
                tooltip.add(String.format(TextFormatting.ITALIC + I18n.translateToLocal("container.shulkerBox.more"), j - i));
            }
        }
    }
}