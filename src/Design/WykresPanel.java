package Design;


import Project.FunkcjaCzujnika;
import Project.FunkcjaLiniowa;

import javax.swing.*;
import java.awt.*;

public class WykresPanel extends JPanel {
    private FunkcjaLiniowa fl;
    private FunkcjaCzujnika fc;
    private int ileFunkcji; //1 - 1 funkcja, 2 - wiele
    double lewo, prawo, gora, dol;
    String lewoOpis, prawoOpis, goraOpis, dolOpis;
    private static final int zapas = 20;
    private static final Color[] KoloryWykresow = {Color.BLUE, Color.green, Color.GRAY, Color.YELLOW, Color.CYAN};
    private String PodswietNaCzerwono;

    public void setFl(FunkcjaLiniowa fl) {
        this.fl = fl;
    }

    public int getIleFunkcji() {
        return ileFunkcji;
    }

    public void setIleFunkcji(int ileFunkcji) {
        this.ileFunkcji = ileFunkcji;
    }

    //    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ileFunkcji==1) {
            WyliczWartosci();
            NarysujUkładWspolrzednych(g);
            NarysujWykres(g, KoloryWykresow[0]);
        }
        else{
            WyliczWartosciDlaListyFunkcji();
            NarysujUkładWspolrzednych(g);
            for (int i=0; i<fc.getListaFunkcji().size();i++){
                fl = fc.getListaFunkcji().get(i);
                NarysujWykres(g, KoloryWykresow[i % 5]);
            }
        }
    }

    private void WyliczWartosciDlaListyFunkcji() {
        if (fc.getListaFunkcji().size()==0){
            dol = -1;
            dolOpis = "-1";
            gora = 1;
            goraOpis = "1";
            lewo = -1;
            prawo = 1;
            if (fc.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk())
                lewoOpis = "-∞";
            else
                lewoOpis = String.valueOf(fc.getDziedzinaCzujnika().getDziedzinaOdWart());
            if (fc.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk())
                prawoOpis = "+∞";
            else
                prawoOpis = String.valueOf(fc.getDziedzinaCzujnika().getDziedzinaDoWart());
        }else{
            double min = MinimalnyXNaLiscieFunkcji();
            double max = MaksymalnyXNaLiscieFunkcji();
            if (min==max) {
                lewo = min - 1;
                prawo = max + 1;
            }else {
                lewo = min - (max - min) / 20;
                prawo = max + (max - min) / 20;
            }
            if (fc.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk())
                lewoOpis = "-∞";
            else
                lewoOpis = String.valueOf(fc.getDziedzinaCzujnika().getDziedzinaOdWart());
            if (fc.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk())
                prawoOpis = "+∞";
            else
                prawoOpis = String.valueOf(fc.getDziedzinaCzujnika().getDziedzinaDoWart());
            min = MinimalnyYNaLiscieFunkcji();
            max = MaksymalnyYNaLiscieFunkcji();
            if (min==max){
                gora = max + 0.5;
                dol = min -0.5;
            }
            else {
                gora = max;
                dol =  min;
            }
            dolOpis = String.valueOf(dol);
            goraOpis = String.valueOf(gora);
        }
    }

    private double MaksymalnyYNaLiscieFunkcji() {
        double result = 1;
        if ((fc.getListaFunkcji().size()>0)&&(fc.getListaFunkcji().get(0).getPunkty().size()>0)) {
            for (int i = 0; i < fc.getListaFunkcji().size(); i++)
                for (int j = 0; j < fc.getListaFunkcji().get(i).getPunkty().size(); j++)
                    if ((fc.getListaFunkcji().get(i).getPunkty().size()>0)&&(fc.getListaFunkcji().get(i).getPunkty().get(j).getY() > result))
                        result = fc.getListaFunkcji().get(i).getPunkty().get(j).getY();
        }
        return result;
    }

    private double MinimalnyYNaLiscieFunkcji() {
        double result = 0;
        if ((fc.getListaFunkcji().size()>0)&&(fc.getListaFunkcji().get(0).getPunkty().size()>0)) {
            for (int i = 0; i < fc.getListaFunkcji().size(); i++)
                for (int j = 0; j < fc.getListaFunkcji().get(i).getPunkty().size(); j++)
                    if ((fc.getListaFunkcji().get(i).getPunkty().size()>0)&&(fc.getListaFunkcji().get(i).getPunkty().get(j).getY() < result))
                        result = fc.getListaFunkcji().get(i).getPunkty().get(j).getY();
        }
        return result;
    }

    private double MaksymalnyXNaLiscieFunkcji() {
        double result;
        if ((fc.getListaFunkcji().size()>0)&&(fc.getListaFunkcji().get(0).getPunkty().size()>0)) {
            result = fc.getListaFunkcji().get(0).getPunkty().get(fc.getListaFunkcji().get(0).getPunkty().size() - 1).getX();
            for (int i=0;i<fc.getListaFunkcji().size();i++)
                if ((fc.getListaFunkcji().get(i).getPunkty().size()>0)&&(fc.getListaFunkcji().get(i).getPunkty().get(fc.getListaFunkcji().get(i).getPunkty().size()-1).getX()>result))
                    result = fc.getListaFunkcji().get(i).getPunkty().get(fc.getListaFunkcji().get(i).getPunkty().size()-1).getX();
        }
        else
            result=0;
        return result;
    }

    private double MinimalnyXNaLiscieFunkcji() {
        double result;
        if ((fc.getListaFunkcji().size()>0)&&(fc.getListaFunkcji().get(0).getPunkty().size()>0)) {
            result = fc.getListaFunkcji().get(0).getPunkty().get(0).getX();
            for (int i = 0; i < fc.getListaFunkcji().size(); i++)
                if ((fc.getListaFunkcji().get(i).getPunkty().size()>0)&&(fc.getListaFunkcji().get(i).getPunkty().get(0).getX() < result))
                    result = fc.getListaFunkcji().get(i).getPunkty().get(0).getX();
        }
        else
            result = 0;
        return result;
    }

    private void NarysujWykres(Graphics g, Color col) {
        if (fl.getNazwa().equals(PodswietNaCzerwono))
            g.setColor(Color.RED);
        else
            g.setColor(col);
        Graphics2D g2 = (Graphics2D) g;
        Stroke tmpStroke = g2.getStroke();
        if (fl.getPunkty().size()==0)
            g.drawLine(PozycjaX(lewo), PozycjaY(0), PozycjaX(prawo), PozycjaY(0));
        else{
            g.drawLine(PozycjaX(lewo), PozycjaY(fl.getPunkty().get(0).getY()), PozycjaX(fl.getPunkty().get(0).getX()), PozycjaY(fl.getPunkty().get(0).getY()));
            float dash1[] = {10.0f};
            for (int i=1; i<fl.getPunkty().size();i++) {
                g.drawLine(PozycjaX(fl.getPunkty().get(i - 1).getX()), PozycjaY(fl.getPunkty().get(i - 1).getY()), PozycjaX(fl.getPunkty().get(i).getX()), PozycjaY(fl.getPunkty().get(i).getY()));
                g2.setStroke(new BasicStroke(1.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        10.0f, dash1, 0.0f));
                g2.drawLine(PozycjaX(fl.getPunkty().get(i - 1).getX()), PozycjaY(fl.getPunkty().get(i - 1).getY()), PozycjaX(fl.getPunkty().get(i-1).getX()),getHeight()-zapas);
                g2.setStroke(tmpStroke);
                g.drawString(String.valueOf(fl.getPunkty().get(i-1).getX()),PozycjaX(fl.getPunkty().get(i - 1).getX()),getHeight()-zapas+15);
            }
            g2.setStroke(new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f));
            g2.drawLine(PozycjaX(fl.getPunkty().get(fl.getPunkty().size() - 1).getX()), PozycjaY(fl.getPunkty().get(fl.getPunkty().size() - 1).getY()), PozycjaX(fl.getPunkty().get(fl.getPunkty().size()-1).getX()),getHeight()-zapas);
            g2.setStroke(tmpStroke);
            g.drawLine(PozycjaX(fl.getPunkty().get(fl.getPunkty().size()-1).getX()), PozycjaY(fl.getPunkty().get(fl.getPunkty().size()-1).getY()), PozycjaX(prawo), PozycjaY(fl.getPunkty().get(fl.getPunkty().size()-1).getY()));
            g.drawString(String.valueOf(fl.getPunkty().get(fl.getPunkty().size()-1).getX()),PozycjaX(fl.getPunkty().get(fl.getPunkty().size() - 1).getX())+10,getHeight()-zapas-10);
        }
    }

    private int PozycjaX(double x){
        return (int)(x*(getWidth()-2*zapas)/(prawo-lewo)+zapas-(lewo*(getWidth()-2*zapas)/(prawo-lewo)));
    }

    private int PozycjaY(double y){
        return (int)(y*(2*zapas-getHeight())/(gora-dol)+zapas-gora*(2*zapas-getHeight())/(gora-dol));
    }

    private void WyliczWartosci() {
        if (fl.getPunkty().size()==0){
            dol = -1;
            dolOpis = "-1";
            gora = 1;
            goraOpis = "1";
            lewo = -1;
            prawo = 1;
            if (fl.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk())
                lewoOpis = "-∞";
            else
                lewoOpis = String.valueOf(fl.getDziedzinaCzujnika().getDziedzinaOdWart());
            if (fl.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk())
                prawoOpis = "+∞";
            else
                prawoOpis = String.valueOf(fl.getDziedzinaCzujnika().getDziedzinaDoWart());
        }
        else
            if (fl.getPunkty().size()==1){
                dol = fl.getPunkty().get(0).getY()-0.5;
                gora = fl.getPunkty().get(0).getY()+0.5;
                if (fl.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk())
                    lewo = fl.getPunkty().get(0).getX()-1;
                else
                    if (fl.getPunkty().get(0).getX()==fl.getDziedzinaCzujnika().getDziedzinaOdWart())
                        lewo = fl.getPunkty().get(0).getX();
                    else
                        lewo = fl.getDziedzinaCzujnika().getDziedzinaOdWart();
                if (fl.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk())
                    prawo = fl.getPunkty().get(0).getX()+1;
                else
                    if (fl.getPunkty().get(0).getX()==fl.getDziedzinaCzujnika().getDziedzinaDoWart())
                        prawo = fl.getPunkty().get(0).getX();
                    else
                        prawo = fl.getDziedzinaCzujnika().getDziedzinaDoWart();
                dolOpis = String.valueOf(dol);
                goraOpis = String.valueOf(gora);
                lewoOpis = String.valueOf(lewo);
                prawoOpis = String.valueOf(prawo);

            }
            else {
                double min = WartoscMinimalna();
                double max = WartoscMaksymalna();
                double dystans = max - min;
                if (dystans==0) {
                    gora = fl.getPunkty().get(0).getY() + 0.5;
                    dol =  fl.getPunkty().get(0).getY() - 0.5;
                }
                else {
                    gora = max;
                    dol =  min;
                }
                dolOpis = String.valueOf(dol);
                goraOpis = String.valueOf(gora);
            }

        if (fl.getPunkty().size()==0){
            if (fl.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk()) {
                lewo = -1;
                lewoOpis = "-∞";
            } else {
                lewo = fl.getDziedzinaCzujnika().getDziedzinaOdWart();
                lewoOpis = String.valueOf(fl.getDziedzinaCzujnika().getDziedzinaOdWart());
            }
            if (fl.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk()) {
                prawo = 1;
                prawoOpis = "+∞";
            } else {
                prawo = fl.getDziedzinaCzujnika().getDziedzinaDoWart();
                prawoOpis = String.valueOf(fl.getDziedzinaCzujnika().getDziedzinaDoWart());
            }
        }
        else
            if (fl.getPunkty().size()==1){
                lewo = fl.getPunkty().get(0).getX()-0.5;
                prawo = fl.getPunkty().get(0).getX()+0.5;
                if (fl.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk()) {
                    lewoOpis = "-∞";
                } else {
                    lewoOpis = String.valueOf(fl.getDziedzinaCzujnika().getDziedzinaOdWart());
                }
                if (fl.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk()) {
                    prawoOpis = "+∞";
                } else {
                    prawoOpis = String.valueOf(fl.getDziedzinaCzujnika().getDziedzinaDoWart());
                }
            }
            else {
                double dystans = fl.getPunkty().get(fl.getPunkty().size()-1).getX() - fl.getPunkty().get(0).getX();
                if (fl.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk()) {
                    lewo = fl.getPunkty().get(0).getX()-dystans/20;
                    lewoOpis = "-∞";
                } else {
                    lewo = fl.getDziedzinaCzujnika().getDziedzinaOdWart();
                    lewoOpis = String.valueOf(lewo);
                }
                if (fl.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk()) {
                    prawo = fl.getPunkty().get(fl.getPunkty().size()-1).getX()+dystans/20;
                    prawoOpis = "+∞";
                } else {
                    prawo = fl.getDziedzinaCzujnika().getDziedzinaDoWart();
                    lewoOpis = String.valueOf(prawo);
                }
                gora = WartoscMaksymalna();
                goraOpis = String.valueOf(gora);
                dol = WartoscMinimalna();
                dolOpis = String.valueOf(dol);
            }
    }

    private double WartoscMinimalna() {
        double result = fl.getPunkty().get(0).getY();
        for (int i=1; i<fl.getPunkty().size();i++)
            if (fl.getPunkty().get(i).getY()<result)
                result =fl.getPunkty().get(i).getY();
        return result;
    }

    private double WartoscMaksymalna() {
        double result = fl.getPunkty().get(0).getY();
        for (int i=1; i<fl.getPunkty().size();i++)
            if (fl.getPunkty().get(i).getY()>result)
                result =fl.getPunkty().get(i).getY();
        return result;
    }

    private void NarysujUkładWspolrzednych(Graphics g) {
        setBackground(Color.BLACK);
        g.setColor(Color.white);
        g.drawLine(0            , getHeight()-zapas     , getWidth()    , getHeight()-zapas);
        g.drawLine(getWidth()-10, getHeight()-zapas-5   , getWidth()    , getHeight()-zapas);
        g.drawLine(getWidth()-10, getHeight()-zapas+5   , getWidth()    , getHeight()-zapas);
        g.drawLine(zapas            , 0                     , zapas         ,     getHeight());
        g.drawLine(zapas            , 0                     , zapas+5   ,10 );
        g.drawLine(zapas            , 0                     , zapas-5   ,10 );
        g.drawString(lewoOpis       , zapas+5                , getHeight()-zapas+15);
        g.drawString(prawoOpis      , getWidth()-30          , getHeight()-zapas+15);
        g.drawString(goraOpis       , 2                      , zapas);
        g.drawString(dolOpis        , 2                      , getHeight()-zapas-5);
    }

    public void ZaktualizujWartoscFunkcji(FunkcjaLiniowa fl){
        this.fl = fl;
        ileFunkcji=1;
        repaint();
    }
    public void ZaktualizujWartoscListyFunkcji(FunkcjaCzujnika funkcjaCzujnika, String s){
        this.fc = funkcjaCzujnika;
        PodswietNaCzerwono = s;
        ileFunkcji=2;
        repaint();
    }
}