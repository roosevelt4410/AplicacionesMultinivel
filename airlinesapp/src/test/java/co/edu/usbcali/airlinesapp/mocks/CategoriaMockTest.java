package co.edu.usbcali.airlinesapp.mocks;

import java.util.Arrays;
import java.util.List;

import co.edu.usbcali.airlinesapp.domain.Categoria;

public class CategoriaMockTest {
	
	
	public static Integer ID_UNO = 1;
    public static Integer ID_DOS = 2;
    public static String NOMBRE_UNO = "C1";
    public static String NOMBRE_DOS = "C2";
    public static String DESCRIPCION_UNO = "C1d";
    public static String DESCRIPCION_DOS = "C2d";

    public static Categoria CATEGORIA_UNO =
            Categoria.builder()
                    .id(ID_UNO)
                    .nombre(NOMBRE_UNO)
                    .descripcion(DESCRIPCION_UNO)
                    .build();

    public static Categoria CATEGORIA_DOS =
            Categoria.builder()
                    .id(ID_DOS)
                    .nombre(NOMBRE_DOS)
                    .descripcion(DESCRIPCION_DOS)
                    .build();

    public static List<Categoria> CATEGORIAS_LIST =
            Arrays.asList(CATEGORIA_UNO, CATEGORIA_DOS);

}
