package com.distribuidora;

import java.io.FileReader;
import java.io.IOError;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

/**
4) Dado o valor de faturamento mensal de uma distribuidora, detalhado por estado:
• SP – R$67.836,43
• RJ – R$36.678,66
• MG – R$29.229,88
• ES – R$27.165,48
• Outros – R$19.849,53

Escreva um programa na linguagem que desejar onde calcule o percentual de representação que cada estado teve dentro do valor total mensal da distribuidora. 
*/
public class App 
{
    public static void main( String[] args )
    {


        
        JFrame frame = new JFrame("Percentual de Faturamento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Layout vertical

        try {
            
            FileReader reader = new FileReader("src/main/resources/faturamentoMensal.json");
            StringBuilder conteudo = new StringBuilder();
            int a;
            while((a = reader.read()) != -1){
                conteudo.append((char) a);
            }
            reader.close();

            JSONArray faturamentoMensal = new JSONArray(conteudo.toString());
            
            //usei um Map pra ter um melhor controle de qual estado é cada faturamento
            Map<String, Double> faturamentos = new HashMap<>();
            
            for(int i=0; i < faturamentoMensal.length(); i++){
                JSONObject item = faturamentoMensal.getJSONObject(i);
                double faturamento = item.getDouble("faturamento");
                String estado = item.getString("estado");

                faturamentos.put(estado, faturamento);
            }

            //dado que percentual(valorDaParte/valorTotal) * 100 

            double faturamentoTotal = 0;
            for(double faturamento : faturamentos.values()){
                faturamentoTotal += faturamento;
            }

            for (Map.Entry<String, Double> en : faturamentos.entrySet()) {
                String estado = en.getKey();
                double faturamento = en.getValue();

                double percentual = (faturamento / faturamentoTotal) * 100;

                JLabel label = new JLabel(estado + " representa " + String.format("%.2f", percentual) + "% do faturamento total.");
                panel.add(label);       
            }

        } catch (Exception e) {
            System.err.println("Erro ao processar os dados: " + e.getMessage());
        } catch(IOError e){
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        frame.add(panel);
        frame.setVisible(true);
    }
}
