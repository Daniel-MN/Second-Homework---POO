#include "utils.h"
#define MAX_CH_CODE 15

void functie_a(char CODE[], int *n, int *m, int matrice_traseu[][], int *pas) {
    int max, indice_max;
    max = CODE[1] - '0';
    indice_max = 1;
    for (int i = 2; i <= 4; i++) {
        if (CODE[i] - '0' > max) {
            max = CODE[i] - '0';
            indice_max = i;
        }
    }
    if (indice_max == 1) {
        (*m)++;
        matrice_traseu[*n][*m] = *pas;
        (*pas)++;
        printf("%d\n", *pas);
        printf("\n");
    }
    if (indice_max == 2) {
        (*n)--;
        printf("%d\n", *m);
        printf("%d\n", *n);
        matrice_traseu[*n][*m] = *pas; 
        printf("%d\n", matrice_traseu[*n][*m]);
        (*pas)++;
        printf("%d\n", *pas);
        printf("\n");
    }
    if (indice_max == 3) {
        (*m)--;
        printf("%d\n", *m);
        printf("%d\n", *n);
        matrice_traseu[*n][*m] = *pas; 
        printf("%d\n", matrice_traseu[*n][*m]);
        (*pas)++;
        printf("%d\n", *pas);
        printf("\n");
    }
    if (indice_max == 4) {
        (*n)++;
        printf("%d\n", *m);
        printf("%d\n", *n);
        matrice_traseu[*n][*m] = *pas; 
        printf("%d\n", matrice_traseu[*n][*m]);
        (*pas)++;
        printf("\n");
    }
}

void SolveTask1() {
    int N, M;
    scanf("%d %d\n", &N, &M);
    int **matrice_traseu = (int **)calloc(N, sizeof(int *));
    if (matrice_traseu == NULL) {
        printf("Alocarea nu a avut loc!");
    }
    for (int i = 0; i < N; i++) {
        matrice_traseu[i] = (int *)calloc()
    }


    int n = 0;
    int m = 0;
    for (int n = 0; n < N; n++) {
        for (int m = 0; m < M; m++) {
            matrice_traseu[n][m] = 0;
        }
    }
    matrice_traseu[0][0] = 1;
    int pas = 2;
    char CODE[MAX_CH_CODE];
    
    while (scanf("%s", CODE) != EOF) {
       
       switch (CODE[0]) {
            case 'a':
                functie_a(CODE, &n, &m, matrice_traseu, &pas);
            break;
            /*case 'b':
                functie_b(CODE, matrice_traseu, &n, &m, &pas);
            break;
            case 'c':
                functie_c(CODE, matrice_traseu, &n, &m, &pas);
            break; */
       }
    }
    
    for (int n = 0; n < N; n++) {
        for (int m = 0; m < M; m++) {
            printf("%d ", matrice_traseu[n][m]);
        }
        printf("\n");
    }
}