/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hl.console;
import hl.facturas33.funciones.ProcesarFactura;
import hl.facturas33.objetos.Comprobante;
import hl.util.HLFilesFunctions;


public class RuntimeExec{

    
    public static void main(String[] args) {
        try
        {
            String PropFile = HLFilesFunctions.WindowsPath("/home/hlcore/hl.properties");
            ProcesarFactura hlc = new ProcesarFactura(PropFile);
            String json = "{\"Proyecto\":\"TINY PEOPLE\",\"IdSucursal\":1,\"IdFactura\":18,\"TipoSerie\":0,\"Serie\":\"\",\"TipoSolicitud\":\"Crear Factura\",\"TokenSolicitud\":\"20180109135614TINYPEOPLE00010000001800\",\"TokenComprobante\":\"TINYPEOPLE00010000001800\",\"CorreoElectronico\":\"vgarcia@arzentia.com\",\"Observaciones\":\"\",\"MetodoPago\":\"01\",\"FormaPago\":\"PUE\",\"UsoCFDI\":\"G01\",\"App\":\"TinyPeopleVentas\",\"AppVersion\":\"1.0.0\",\"IdComputadora\":0,\"Computadora\":\"\",\"UrlSolicitud\":\"http://192.168.0.2:8080/HLWebServices/Interface33\",\"XML64\":\"PD94bWwgdmVyc2lvbj0iMS4wIj8+DQo8Y2ZkaTpDb21wcm9iYW50ZSB4bWxuczp4c2k9Imh0dHA6Ly93d3cudzMub3JnLzIwMDEvWE1MU2NoZW1hLWluc3RhbmNlIiB4c2k6c2NoZW1hTG9jYXRpb249Imh0dHA6Ly93d3cuc2F0LmdvYi5teC9zaXRpb19pbnRlcm5ldC9jZmQvMy9jZmR2MzMueHNkIiBWZXJzaW9uPSIzLjMiIFNlcmllPSIiIEZvbGlvPSIxOCIgRmVjaGE9IjIwMTgtMDEtMDlUMTM6NTU6NDIiIFNlbGxvPSJzaW4gc2VsbG8iIEZvcm1hUGFnbz0iMDEiIENlcnRpZmljYWRvPSIiIE5vQ2VydGlmaWNhZG89IiIgQ29uZGljaW9uZXNEZVBhZ289IkNvbnRhZG8iIFN1YlRvdGFsPSIxNzM3LjA3IiBNb25lZGE9Ik1YTiIgVG90YWw9IjE3MzcuMDY5MDAwMjc3LjkzMTA0MCIgVGlwb0RlQ29tcHJvYmFudGU9IkkiIE1ldG9kb1BhZ289IlBVRSIgTHVnYXJFeHBlZGljaW9uPSIiIFByb3llY3RvPSJUSU5ZIFBFT1BMRSIgSWRTdWN1cnNhbD0iMSIgVGlwb1NlcmllPSIwIiB4bWxuczpjZmRpPSJodHRwOi8vd3d3LnNhdC5nb2IubXgvY2ZkLzMiPjxjZmRpOkNvbmNlcHRvcz48Y2ZkaTpDb25jZXB0byBDbGF2ZVByb2RTZXJ2PSI1NjEwMTgwMCIgTm9JZGVudGlmaWNhY2lvbj0iMTEtMjEwMCIgQ2FudGlkYWQ9IjEiIENsYXZlVW5pZGFkPSJIODciIFVuaWRhZD0iUFpTIiBEZXNjcmlwY2lvbj0iQWxtb2hhZGFzIiBWYWxvclVuaXRhcmlvPSI0NDguMjc1OTAwIiBJbXBvcnRlPSI0NDguMjc1OSIgRGVzY3VlbnRvPSIwLjAwIiBTdWJ0b3RhbEFjdW11bGFkbz0iNDQ4LjI3NTkwMCI+PGNmZGk6SW1wdWVzdG9zPjxjZmRpOlRyYXNsYWRvcz48Y2ZkaTpUcmFzbGFkbyBCYXNlPSI0NDguMjc1OSIgSW1wdWVzdG89IjAwMiIgVGlwb0ZhY3Rvcj0iVGFzYSIgVGFzYU9DdW90YT0iMC4xNjAwMDAiIEltcG9ydGU9IjcxLjcyNDE0NCIgSVZBQWN1bXVsYWRvPSI3MS43MjQxNDQiLz48L2NmZGk6VHJhc2xhZG9zPjwvY2ZkaTpJbXB1ZXN0b3M+PC9jZmRpOkNvbmNlcHRvPjxjZmRpOkNvbmNlcHRvIENsYXZlUHJvZFNlcnY9IjU2MTAxODAwIiBOb0lkZW50aWZpY2FjaW9uPSIxMS0xMTAwIiBDYW50aWRhZD0iMSIgQ2xhdmVVbmlkYWQ9Ikg4NyIgVW5pZGFkPSJQWlMiIERlc2NyaXBjaW9uPSJCbGFua2llcyIgVmFsb3JVbml0YXJpbz0iMzA2LjAzNDUwMCIgSW1wb3J0ZT0iMzA2LjAzNDUiIERlc2N1ZW50bz0iMC4wMCIgU3VidG90YWxBY3VtdWxhZG89Ijc1NC4zMTA0MDAiPjxjZmRpOkltcHVlc3Rvcz48Y2ZkaTpUcmFzbGFkb3M+PGNmZGk6VHJhc2xhZG8gQmFzZT0iMzA2LjAzNDUiIEltcHVlc3RvPSIwMDIiIFRpcG9GYWN0b3I9IlRhc2EiIFRhc2FPQ3VvdGE9IjAuMTYwMDAwIiBJbXBvcnRlPSI0OC45NjU1MiIgSVZBQWN1bXVsYWRvPSIxMjAuNjg5NjY0Ii8+PC9jZmRpOlRyYXNsYWRvcz48L2NmZGk6SW1wdWVzdG9zPjwvY2ZkaTpDb25jZXB0bz48Y2ZkaTpDb25jZXB0byBDbGF2ZVByb2RTZXJ2PSI2MDE0MTAwMCIgTm9JZGVudGlmaWNhY2lvbj0iNy1DS1IzNCAiIENhbnRpZGFkPSIxIiBDbGF2ZVVuaWRhZD0iSDg3IiBVbmlkYWQ9IlBaUyIgRGVzY3JpcGNpb249IkhBTUFDQSBCT1VOQ0VSIENVUlZFIiBWYWxvclVuaXRhcmlvPSI5ODIuNzU4NjAwIiBJbXBvcnRlPSI5ODIuNzU4NiIgRGVzY3VlbnRvPSIwLjAwIiBTdWJ0b3RhbEFjdW11bGFkbz0iMTczNy4wNjkwMDAiPjxjZmRpOkltcHVlc3Rvcz48Y2ZkaTpUcmFzbGFkb3M+PGNmZGk6VHJhc2xhZG8gQmFzZT0iOTgyLjc1ODYiIEltcHVlc3RvPSIwMDIiIFRpcG9GYWN0b3I9IlRhc2EiIFRhc2FPQ3VvdGE9IjAuMTYwMDAwIiBJbXBvcnRlPSIxNTcuMjQxMzc2IiBJVkFBY3VtdWxhZG89IjI3Ny45MzEwNDAiLz48L2NmZGk6VHJhc2xhZG9zPjwvY2ZkaTpJbXB1ZXN0b3M+PC9jZmRpOkNvbmNlcHRvPjwvY2ZkaTpDb25jZXB0b3M+PGNmZGk6UmVjZXB0b3IgUmZjPSJDREkwOTAzMThLNjgiIE5vbWJyZT0iQ09SUE9SQVRJVk8gRElFQ0lTSUVURSBTLkEgREUgQ1YiIFVzb0NGREk9IkcwMSIvPjxjZmRpOkltcHVlc3RvcyBUb3RhbEltcHVlc3Rvc1RyYXNsYWRhZG9zPSIyNzcuOTMiIFRvdGFsQWN1bXVsYWRvPSIxNzM3LjA2OTAwMDI3Ny45MzEwNDAiPjxjZmRpOlRyYXNsYWRvcz48Y2ZkaTpUcmFzbGFkbyBJbXB1ZXN0bz0iMDAyIiBUaXBvRmFjdG9yPSJUYXNhIiBUYXNhT0N1b3RhPSIwLjE2MDAwMCIgSW1wb3J0ZT0iMjc3LjkzMTA0MCIvPjwvY2ZkaTpUcmFzbGFkb3M+PC9jZmRpOkltcHVlc3Rvcz48L2NmZGk6Q29tcHJvYmFudGU+DQo=\"}";


            

            
            hlc.EjecutarFactura(json, true);
            
            //System.out.println(hlc.getSolicitudFacturaJson());

        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
    }
}