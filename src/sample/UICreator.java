package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by sebo on 11/20/18.
 * janko.sebastian@gmail.com
 */
public class UICreator {
    private MetadataRetriever mdr;
    private ListView<FactTable> factTablesListView;
    private TreeView<Attribute> attributesTree;
    private TreeView<Dimension> dimensionsTree;

    UICreator(Stage stage) {
        mdr = new MetadataRetriever();
        factTablesListView = getFactTablesList();
        attributesTree = getAttributesTree();
        dimensionsTree = getDimensionsTree();
        createUI(stage);
    }

    private void createUI(Stage stage) {
        GridPane grid = new GridPane();

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(30);
        grid.getColumnConstraints().add(column);

        column = new ColumnConstraints();
        column.setPercentWidth(70);
        grid.getColumnConstraints().add(column);


        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));


        TableView<String> table = new TableView<>();
        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);

        factTablesListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    setDimensionsTree(newValue);
                    setAttributesTree(newValue);
                }
        );

        grid.add(new Button("Play"), 0, 0);
        grid.add(factTablesListView, 0, 1);
        grid.add(attributesTree, 0, 2);
        grid.add(dimensionsTree, 0, 3);
        grid.add(new Label("SQL QUERY"), 1, 0, 1, 2);
        grid.add(table, 1, 2, 1, 2);
        fillTable(table);


        Scene scene = new Scene(new VBox(), 1100, 600);
        ((VBox) scene.getRoot()).getChildren().addAll(grid);
        stage.setScene(scene);
        stage.setTitle("Star Model Viewer");

        stage.show();
    }

    private void setAttributesTree(FactTable ft) {
        attributesTree.getRoot().getChildren().clear();
        ArrayList<Attribute> attributes = mdr.getAttributes(ft);
        Attribute rootAt = new Attribute("Mjere");

        attributesTree.getRoot().setValue(rootAt);

        for (Attribute at : attributes) {
            final CheckBoxTreeItem<Attribute> checkBoxTreeItem =
                    new CheckBoxTreeItem<>(at);
            attributesTree.getRoot().getChildren().add(checkBoxTreeItem);
        }
    }

    private void setDimensionsTree(FactTable ft) {
        dimensionsTree.getRoot().getChildren().clear();
        ArrayList<Dimension> dimensions = mdr.getDimensions(ft);
        dimensionsTree.getRoot().setValue(new Dimension("Dimenzije"));

        for (Dimension d : dimensions) {
            TreeItem<Dimension> dLeaf = new TreeItem<>(d);
            boolean found = false;
            for (TreeItem<Dimension> depNode : dimensionsTree.getRoot().getChildren()) {
                if (depNode.getValue().getNazTablica().equals(d.getNazTablica())) {
                    depNode.getChildren().add(dLeaf);
                    found = true;
                    break;
                }
            }
            // TODO sta je tu node sta je leaf?
            if (!found) {
                TreeItem<Dimension> dNode = new TreeItem<>(new Dimension(d.getNazTablica()));
                dimensionsTree.getRoot().getChildren().add(dNode);
                dNode.getChildren().add(dLeaf);
            }
        }
    }

    private void fillTable(TableView<String> table) {
        table.getItems().clear();
        table.getColumns().clear();

        ArrayList<TableColumn> dateColumns = new ArrayList<>();
        ObservableList<ObservableList> rowData = FXCollections.observableArrayList();
        ObservableList<String> rows = FXCollections.observableArrayList();

        //dodaj stupac query i sve datume
        TableColumn query = new TableColumn("Query");
        query.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(0).toString());
            }
        });


        /*for (int j = 0; j < res.getDates().size(); j++) {
            final int i = j + 1;
            TableColumn tc = new TableColumn(res.getDates().get(j));
            dateColumns.add(tc);
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    if (param.getValue().get(i) != null) {
                        return new SimpleStringProperty(param.getValue().get(i).toString());
                    }
                    return new SimpleStringProperty("");
                }
            });
        }

        for (ArrayList<String> row : res.getRows()) {
            for (String s : row) {
                if (s == null) {
                    rows.add("0");
                } else {
                    rows.add(s);
                }
            }
            rowData.add(rows);
            rows = FXCollections.observableArrayList();
        }

        //dodaj stupce u tablicu
        table.getColumns().addAll(query);
        for (TableColumn tc : dateColumns) {
            table.getColumns().add(tc);
        }

        table.setItems(rowData);*/
    }


    private ListView<FactTable> getFactTablesList() {
        ListView<FactTable> listView = new ListView<>();
        ObservableList<FactTable> itemsList = FXCollections.observableArrayList();
        ArrayList<FactTable> factTables = mdr.getFactTables();

        itemsList.addAll(factTables);
        listView.setItems(itemsList);

        return listView;
    }

    private TreeView<Attribute> getAttributesTree() {
        CheckBoxTreeItem<Attribute> rootItem =
                new CheckBoxTreeItem<>(new Attribute());
        rootItem.setExpanded(true);

        final TreeView<Attribute> tree = new TreeView<>(rootItem);
        tree.setEditable(true);

        tree.setCellFactory(CheckBoxTreeCell.forTreeView());
        tree.setRoot(rootItem);
        tree.setShowRoot(true);
        return tree;

        //TODO dodati tree of trees
    }

    private TreeView<Dimension> getDimensionsTree() {
        CheckBoxTreeItem<Dimension> rootItem =
                new CheckBoxTreeItem<>();
        rootItem.setExpanded(true);

        final TreeView<Dimension> tree = new TreeView<>(rootItem);
        tree.setEditable(true);

        tree.setCellFactory(CheckBoxTreeCell.forTreeView());

        tree.setRoot(rootItem);
        tree.setShowRoot(true);
        return tree;
    }

}
