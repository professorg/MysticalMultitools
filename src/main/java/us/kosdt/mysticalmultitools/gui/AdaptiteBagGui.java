package us.kosdt.mysticalmultitools.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;
import us.kosdt.mysticalmultitools.MysticalMultitools;

public class AdaptiteBagGui extends GuiContainer {

    // Size of window, pixels
    private float xSize_lo;
    private float ySize_lo;

    private GuiButton mButtonFirst;
    private GuiButton mButtonPrev;
    private GuiButton mButtonNext;
    private GuiButton mButtonLast;

    private static final int BUTTON_WIDTH = 16;
    private static final int BUTTON_HEIGHT = 16;

    private int cid;

    private static final ResourceLocation ICON_LOCATION = new ResourceLocation(MysticalMultitools.MODID, "textures/gui/adaptite_bag_inventory.png");

    private final AdaptiteBagInventory inventory;

    public AdaptiteBagGui(EntityPlayer player, InventoryPlayer inventoryPlayer, AdaptiteBagInventory inventoryCustom) {
        super(new AdaptiteBagContainer(player, inventoryPlayer, inventoryCustom));
        this.inventory = inventoryCustom;
    }

    @Override
    public void initGui() {
        super.initGui();
        cid = 0;
        buttonList.add(
                mButtonFirst = new GuiButton(cid++, guiLeft + 54, guiTop + 16, BUTTON_WIDTH, BUTTON_HEIGHT, "<<")
        );
        buttonList.add(
                mButtonPrev = new GuiButton(cid++, guiLeft + 54 + BUTTON_WIDTH, guiTop + 16, BUTTON_WIDTH, BUTTON_HEIGHT, "<")
        );
        buttonList.add(
                mButtonNext = new GuiButton(cid++, guiLeft + 54 + 2 * BUTTON_WIDTH, guiTop + 16, BUTTON_WIDTH, BUTTON_HEIGHT, ">")
        );
        buttonList.add(
                mButtonLast = new GuiButton(cid++, guiLeft + 54 + 3 * BUTTON_WIDTH, guiTop + 16, BUTTON_WIDTH, BUTTON_HEIGHT, ">>")
        );
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button == mButtonFirst) {
            inventory.first();
        } else if (button == mButtonPrev) {
            inventory.prev();
        } else if (button == mButtonNext) {
            inventory.next();
        } else if (button == mButtonLast) {
            inventory.last();
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!inventory.isEmpty()) {
            fontRenderer.drawString(String.format("%d/%d", inventory.selected + 1, inventory.getSizeInventory()), guiLeft + 54, guiTop + 34, 0x000000);
            fontRenderer.drawString(String.format("Count: %d", inventory.getSelectedEntry().getValue()), guiLeft + 54, guiTop + 44, 0x000000);
            fontRenderer.drawString(String.format("Durability: %d", inventory.getSelectedEntry().getKey().durability), guiLeft + 54, guiTop + 54, 0x000000);
            fontRenderer.drawString(String.format("Speed: %d", inventory.getSelectedEntry().getKey().speed), guiLeft + 54, guiTop + 64, 0x000000);
            fontRenderer.drawString(String.format("ItemStack Count: %d", inventory.getStackInSlot(AdaptiteBagConstants.OUTPUT_SLOT).getCount()), 16, 16, 0xFFFFFF);
        }
        renderHoveredToolTip(mouseX, mouseY); // For some reason not in super
        xSize_lo = mouseX;
        ySize_lo = mouseY;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(ICON_LOCATION);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

}
