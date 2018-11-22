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
    MetadataRetriever mdr;

    UICreator(Stage stage) {
        mdr = new MetadataRetriever();
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
        // grid.setPrefSize(1000, 400);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));


        TableView<String> table = new TableView<>();
        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);

        grid.add(new Label("SQL QUERY"), 1, 0);
        grid.add(getTablesList(), 0, 0);
        grid.add(getDimensionsTree(), 0, 1);
        grid.add(getAttributesTree(), 0, 2);
        grid.add(table, 1, 1, 1, 2);
        fillTable(table);


        Scene scene = new Scene(new VBox(), 1100, 600);
        ((VBox) scene.getRoot()).getChildren().addAll(grid);
        stage.setScene(scene);
        stage.setTitle("Star Model Viewer");

        stage.show();
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


    private ListView<String> getTablesList() {
        ListView<String> listView = new ListView<>();
        ObservableList<String> itemsList = FXCollections.observableArrayList();
        for (int i = 1; i < 6; i++) {
            itemsList.add("Msg " + i);
        }
        listView.setItems(itemsList);
        return listView;
    }

    private TreeView<String> getDimensionsTree() {
        CheckBoxTreeItem<String> rootItem =
                new CheckBoxTreeItem<String>("View Source Files");
        rootItem.setExpanded(true);

        final TreeView<String> tree = new TreeView<>(rootItem);
        tree.setEditable(true);

        tree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        for (int i = 0; i < 8; i++) {
            final CheckBoxTreeItem<String> checkBoxTreeItem =
                    new CheckBoxTreeItem<String>("Sample" + (i + 1));
            rootItem.getChildren().add(checkBoxTreeItem);
        }

        tree.setRoot(rootItem);
        tree.setShowRoot(true);
        return tree;
    }

    private TreeView<String> getAttributesTree() {
        CheckBoxTreeItem<String> rootItem =
                new CheckBoxTreeItem<String>("View Source Files");
        rootItem.setExpanded(true);

        final TreeView<String> tree = new TreeView<>(rootItem);
        tree.setEditable(true);

        tree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        for (int i = 0; i < 8; i++) {
            final CheckBoxTreeItem<String> checkBoxTreeItem =
                    new CheckBoxTreeItem<String>("Sample" + (i + 1));
            rootItem.getChildren().add(checkBoxTreeItem);
        }

        tree.setRoot(rootItem);
        tree.setShowRoot(true);
        return tree;

        //TODO dodati tree of trees
    }
}
