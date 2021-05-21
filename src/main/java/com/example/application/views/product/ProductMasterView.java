package com.example.application.views.product;

import com.example.application.backend.model.Product;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.service.WarehouseService;
import com.example.application.components.AdminButton;
import com.example.application.security.SecurityConfiguration;
import com.example.application.views.product.form.ProductForm;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Route(value = "product-master", layout = MainView.class)
@PageTitle("Product Master")
public class ProductMasterView extends VerticalLayout {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int NOTIFICATION_DEFAULT_DURATION = 5000;

    private List<Product> productList;
    private ProductService productService;
    private WarehouseService warehouseService;
    private Grid<Product> grid = new Grid<>(Product.class);

    public ProductMasterView(ProductService service, WarehouseService warehouseService) {
        addClassName("product-master-view");

        this.productService = service;
        this.warehouseService = warehouseService;
        this.productList = productService.findAll();

        setPadding(true); // add some padding around

        add(rowButtonsToolBar());
        add(createGridProducts());

    }

    /**
     * Carga la lista de productos ordenada en el grid
     */
    private void loadProductsGrid() {
        ListDataProvider<Product> productProvider;
        productProvider = DataProvider.ofCollection(this.productList);
        productProvider.setSortOrder(Product::getName, SortDirection.ASCENDING);
        grid.setDataProvider(productProvider);
    }

    /**
     * Crea el grid de productos y lo configura
     * @return Component grid
     */
    private Component createGridProducts(){

        loadProductsGrid();

        //grid.setItems(productList);
        //grid.removeColumnByKey("id");

        // indicamos columnas y el orden
        grid.setColumns("warehouse.name", "name", "description", "family", "price");
        grid.getColumnByKey("warehouse.name").setHeader("Warehouse").setFooter("Total: " + productList.size() + " products");
        grid.addColumn(
                new ComponentRenderer<>(
                        product -> {
                            Checkbox checkbox = new Checkbox();
                            checkbox.setReadOnly(true);
                            checkbox.setValue( product.getActive());

                            return checkbox;
                        }
                )
        ).setHeader("Active").setKey("active").setFlexGrow(0).setWidth("80px").setHeader("Active");

        // si es Admin crea los botones de actualizar y eliminar productos para el grid
        if (SecurityConfiguration.isAdmin()) {
            grid.addComponentColumn(product -> updateProductButton(product)).setFlexGrow(0).setWidth("120px").setHeader("");
            grid.addComponentColumn(product -> removeProductButton(product)).setFlexGrow(0).setWidth("120px").setHeader("");
        }

        // estilos del grid
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES); //para que las filas pares e impares tengan colores diferentes

        return grid;
    }


    /**
     * Crea la barra de herramientas superior con los dos botones de crear y actualizar producto.
     * @return Component componente web
     */
    private Component rowButtonsToolBar(){

        HorizontalLayout toolBarLayout = new HorizontalLayout();
        toolBarLayout.setPadding(true);
        toolBarLayout.setWidthFull();

        Button addProduct = new AdminButton();
        addProduct = new AdminButton("Add Product", clickEvent -> createProductFormDialog(clickEvent));
        addProduct.getElement().getStyle().set("margin-right", "auto");
        addProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button refreshProducts = new Button();
        refreshProducts = new Button("Refresh Products", clickEvent -> refreshProducts(clickEvent));

        toolBarLayout.add(addProduct, refreshProducts);

        return toolBarLayout;

    }



    /**
     * Crea el formulario en un dialog para crear un nuevo producto.
     * @param e evento
     */
    private void createProductFormDialog(ClickEvent e) {
        // define form dialog
        ProductForm productForm = new ProductForm(this.warehouseService, "New Product");
        productForm.setWidth("700px");
        productForm.setCloseOnEsc(true);
        productForm.setCloseOnOutsideClick(false);

        // bind form dialog with product entity
        productForm.setProduct(new Product());

        // define form dialog view callback
        productForm.addOpenedChangeListener(event -> {
            if(!event.isOpened()) {
                if (productForm.getDialogResult() == ProductForm.DIALOG_RESULT.SAVE)
                    try {
                        // save product entity
                        productService.save(productForm.getProduct());

                        // refresh grid
                        refreshProducts(null);

                        Notification.show("Product Saved", NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());

                        Notification.show(ex.getMessage(), NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
                    }
            }
        });

        // open form dialog view
        productForm.open();
    }


    /**
     * Crea el botón para actualizar un producto del grid. También se crea el dialog con el formulario de actualización.
     * @param product
     * @return
     */
    private Button updateProductButton(Product product) {
        Button button = new AdminButton("Update", clickEvent -> {
            // define form dialog
            ProductForm productForm = new ProductForm(this.warehouseService, "Update Product");
            productForm.setWidth("700px");
            productForm.setCloseOnEsc(true);
            productForm.setCloseOnOutsideClick(false);

            // bind form dialog with product entity
            productForm.setProduct(product);

            // define form dialog view callback
            productForm.addOpenedChangeListener(event -> {
                if(!event.isOpened()) {
                    if (productForm.getDialogResult() == ProductForm.DIALOG_RESULT.SAVE)
                        try {
                            // save product entity
                            productService.save(productForm.getProduct());

                            // refresh grid
                            refreshProducts(null);

                            Notification.show("Product Updated", NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
                        } catch (Exception ex) {
                            logger.error(ex.getMessage());

                            Notification.show(ex.getMessage(), NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
                        }
                }
            });

            // open form dialog view
            productForm.open();
        });

        return button;
    }


    /**
     * Crea el botón para eliminar un producto del grid
     * @param product
     * @return
     */
    private Button removeProductButton(Product product) {
        Button button = new AdminButton("Remove", clickEvent -> {
            try {
                // delete product
                // TODO eliminar la asociación del producto con stock antes para poder eliminar el producto
                productService.delete(product);

                // refresh grid
                refreshProducts(null);

                Notification.show("Product Deleted", NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
            } catch (Exception ex) {
                logger.error(ex.getMessage());

                Notification.show(ex.getMessage(), NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
            }
        });

        button.addThemeVariants(ButtonVariant.LUMO_ERROR);

        return button;
    }


    /**
     * Método para actualizar el grid de productos
     * @param e evento click
     */
    private void refreshProducts(ClickEvent e) {
        try {
            // load products from service
            this.productList = productService.findAll();

            // fill grid with data
            loadProductsGrid();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Notification.show(ex.getMessage());
        }
    }



    /*
    private Dialog dialogCreateProduct(){

        Dialog dialog = new Dialog();
        dialog.add(new H1("New Product"));
        FormLayout form = createProductFormLayout();
        dialog.add(form);
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        Span message = new Span();

        Button confirmButton = new Button("Confirm", event -> {
            message.setText("Confirmed!");

            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event -> {
            message.setText("Cancelled...");
            dialog.close();
        });
        Shortcuts.addShortcutListener(dialog, () -> {
            message.setText("Cancelled...");
            dialog.close();
        }, Key.ESCAPE);

        dialog.add(new HtmlComponent("hr"));
        dialog.add(new HtmlComponent("br"));
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.add(new Div( confirmButton, cancelButton));

        return dialog;
    }*/




}
