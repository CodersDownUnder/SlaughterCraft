package trhod177.bm;

import java.io.File;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import trhod177.bm.creativetabs.ButcheryModCreativeTab;
import trhod177.bm.creativetabs.ButcheryModCreativeTab2;
import trhod177.bm.creativetabs.ButcheryModCreativeTab3;
import trhod177.bm.handlers.ConfigHandler;
import trhod177.bm.handlers.RecipeHandler;
import trhod177.bm.handlers.SCOreDictionaryHandler;
import trhod177.bm.handlers.SlaughterCraftEventHandler;
import trhod177.bm.init.ArmourInit;
import trhod177.bm.init.BlockInit;
import trhod177.bm.init.ItemInit;
import trhod177.bm.init.MobDrops;
import trhod177.bm.proxy.CommonProxy;
import trhod177.bm.worldgen.WorldGenCustomOres;
import trhod177.bm.worldgen.WorldGenCustomStructures;

@Mod(modid   = References.MODID, name = References.NAME, version = References.VERSION, updateJSON = "https://raw.githubusercontent.com/CodersDownUnder/UpdateJsons/master/slaughtercraftversion.json")
public class SlaughterCraft {
    public static final CreativeTabs BMCT  = new ButcheryModCreativeTab("BlockInit.cowcarcass");
    public static final CreativeTabs BMCT2 = new ButcheryModCreativeTab2("ItemInit.cowbelly");
    public static final CreativeTabs BMCT3 = new ButcheryModCreativeTab3("ItemInit.butcherknife");
    
    public static File config;
    
    
    @SidedProxy(clientSide = References.CLIENTPROXY, serverSide = References.COMMONPROXY)
    
    public static CommonProxy proxy;
    @Mod.Instance(References.MODID)
    public static SlaughterCraft instance;


    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item,
                                                   meta,
                                                   new ModelResourceLocation(References.MODID + ":" + id, "inventory"));
        new ModelResourceLocation(item.getRegistryName() + "variantName", "inventory");
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BlockInit.register(event.getRegistry());
            
        }

        @SubscribeEvent
        public static void registerItems(ModelRegistryEvent event) {
            ItemInit.registerModels();
            ArmourInit.registerModels();
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ItemInit.register(event.getRegistry());
            ArmourInit.register(event.getRegistry());
            BlockInit.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ItemInit.registerModels();
            ArmourInit.registerModels();
            BlockInit.registerModels();
        }
    }
    
    

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(ConfigHandler.class);
        ConfigHandler.registerConfig(event);
        //OBJLoader.INSTANCE.addDomain(References.MODID);
        
        GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
        GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                                            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                                            GlStateManager.SourceFactor.ONE,
                                            GlStateManager.DestFactor.ZERO);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        
        MinecraftForge.EVENT_BUS.register(new SlaughterCraftEventHandler());
        
    }
    
    @EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init(event);
        MinecraftForge.EVENT_BUS.register(new MobDrops());
        SCOreDictionaryHandler.registerOres();
        RecipeHandler.registerShapedRecipes();
        RecipeHandler.registerShapelessRecipes();
        RecipeHandler.registerFurnaceRecipes();
       
        
        
        
      
        
        
    }
    
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        
    	
    }
    



  
   
}



