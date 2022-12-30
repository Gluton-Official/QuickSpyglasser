package dev.gluton.quickspyglasser

import com.google.common.base.Suppliers
import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.RegistrarManager
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import org.apache.logging.log4j.LogManager
import java.util.function.Supplier

object QuickSpyglasser {
	const val ID = "quickspyglasser"

	val logger = LogManager.getLogger(ID)

	// TOOD: make memoize delegate
	val registers = Suppliers.memoize { RegistrarManager.get(ID) }

	val exampleTab: CreativeTabRegistry.TabSupplier = CreativeTabRegistry.create(
		ResourceLocation(ID, "example_tab"),
		Supplier { ItemStack(exampleItem.get()) }
	)

	val items = DeferredRegister.create(ID, Registries.ITEM)
	val exampleItem = items.register("example_item") { Item(Item.Properties().`arch$tab`(exampleTab)) }

	fun init() {
		logger.info("Initializing QuickSpyglasser")
		items.register()

		logger.info("Config director: ${QuickSpyglasserExpectPlatform.configDirectory()}")
	}
}
