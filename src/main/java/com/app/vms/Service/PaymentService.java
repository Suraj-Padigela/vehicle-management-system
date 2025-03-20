package com.app.vms.Service;

import com.app.vms.entity.Invoice;
import com.app.vms.entity.Payment;
import com.app.vms.repository.InvoiceRepository;
import com.app.vms.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    InvoiceRepository invoiceRepository;

    public String makePayment(Long invoiceId, double amount, String paymentMethod){
        Optional<Invoice> invoiceOpt = invoiceRepository.findByIdAndIsPaidFalse(invoiceId);

        if(invoiceOpt.isPresent()){
            Invoice invoice = invoiceOpt.get();

            double totalDue = invoice.getTotalAmount() + invoice.getLateFee();
            if(amount < totalDue){
                return "Insufficient payment amount. Total due: "+totalDue;
            }

            Payment payment = new Payment();
            payment.setInvoice(invoice);
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentMethod(paymentMethod);

            paymentRepository.save(payment);

            invoice.setPaid(true);
            invoiceRepository.save(invoice);

            return "Payment successful. Invoice ID: " + invoiceId;
        }
        return "Invoice not found or already paid.";
    }


}
